package com.meta_forge_platform.runtime.application.service.impl;

import com.meta_forge_platform.platform.domain.entity.DpWorkflow;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowState;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowTransition;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowStateRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowTransitionRepository;
import com.meta_forge_platform.runtime.application.dto.record.*;
import com.meta_forge_platform.runtime.application.mapper.AppRecordMapper;
import com.meta_forge_platform.runtime.application.service.AppRecordService;
import com.meta_forge_platform.runtime.domain.entity.AppRecord;
import com.meta_forge_platform.runtime.domain.entity.AppRecordStateHistory;
import com.meta_forge_platform.runtime.infrastructure.repository.AppRecordRepository;
import com.meta_forge_platform.runtime.infrastructure.repository.AppRecordStateHistoryRepository;
import com.meta_forge_platform.shared.application.BaseServiceImpl;
import com.meta_forge_platform.shared.domain.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AppRecordServiceImpl
        extends BaseServiceImpl<AppRecord, AppRecordDto, CreateAppRecordCmd, UpdateAppRecordCmd, Long>
        implements AppRecordService {

    private final AppRecordRepository recordRepository;
    private final AppRecordStateHistoryRepository historyRepository;
    private final DpEntityRepository entityRepository;
    private final DpWorkflowRepository workflowRepository;
    private final DpWorkflowStateRepository stateRepository;
    private final DpWorkflowTransitionRepository transitionRepository;
    private final AppRecordMapper mapper;

    public AppRecordServiceImpl(AppRecordRepository recordRepository,
                                AppRecordStateHistoryRepository historyRepository,
                                DpEntityRepository entityRepository,
                                DpWorkflowRepository workflowRepository,
                                DpWorkflowStateRepository stateRepository,
                                DpWorkflowTransitionRepository transitionRepository,
                                AppRecordMapper mapper) {
        super(recordRepository);
        this.recordRepository = recordRepository;
        this.historyRepository = historyRepository;
        this.entityRepository = entityRepository;
        this.workflowRepository = workflowRepository;
        this.stateRepository = stateRepository;
        this.transitionRepository = transitionRepository;
        this.mapper = mapper;
    }

    @Override protected AppRecordDto toDto(AppRecord e) { return mapper.toDto(e); }
    @Override protected AppRecord toEntity(CreateAppRecordCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(AppRecord e, UpdateAppRecordCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void afterToEntity(AppRecord entity, CreateAppRecordCmd cmd) {
        // Set entity
        var dpEntity = entityRepository.findActiveById(cmd.getEntityId())
                .orElseThrow(() -> AppException.notFound("Entity", cmd.getEntityId()));
        entity.setEntity(dpEntity);

        // Set parent/root record
        if (cmd.getParentRecordId() != null) {
            entity.setParentRecord(recordRepository.findActiveById(cmd.getParentRecordId())
                    .orElseThrow(() -> AppException.notFound("ParentRecord", cmd.getParentRecordId())));
        }
        if (cmd.getRootRecordId() != null) {
            entity.setRootRecord(recordRepository.findActiveById(cmd.getRootRecordId())
                    .orElseThrow(() -> AppException.notFound("RootRecord", cmd.getRootRecordId())));
        }

        // Set initial workflow state tự động
        workflowRepository.findByEntity_IdAndIsDefaultTrueAndIsDeletedFalse(cmd.getEntityId())
                .flatMap(wf -> stateRepository.findByWorkflow_IdAndIsInitialTrueAndIsDeletedFalse(wf.getId()))
                .ifPresent(entity::setCurrentState);
    }

    @Override
    protected void afterCreate(AppRecord entity, CreateAppRecordCmd cmd) {
        // Ghi lịch sử state ban đầu nếu có workflow
        if (entity.getCurrentState() != null) {
            saveStateHistory(entity, null, entity.getCurrentState(), null, "CREATE", null);
        }
    }

    @Override
    protected Specification<AppRecord> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("recordCode")), p),
                cb.like(cb.lower(root.get("displayName")), p)
        );
    }

    // ── Workflow transition ───────────────────────────────────────────────────

    @Override
    @Transactional
    public AppRecordDto transition(Long recordId, TransitionAppRecordCmd cmd) {
        AppRecord record = recordRepository.findActiveById(recordId)
                .orElseThrow(() -> AppException.notFound("Record", recordId));

        // Kiểm tra optimistic lock
        if (!record.getVersionNo().equals(cmd.getVersionNo()))
            throw AppException.optimisticLock();

        DpWorkflowState fromState = record.getCurrentState();
        DpWorkflowState toState = stateRepository.findActiveById(cmd.getToStateId())
                .orElseThrow(() -> AppException.notFound("ToState", cmd.getToStateId()));

        // Kiểm tra transition hợp lệ
        DpWorkflowTransition transition = null;
        if (fromState != null) {
            transition = transitionRepository
                    .findTransition(fromState.getId(), toState.getId())
                    .orElseThrow(() -> AppException.badRequest(
                            String.format("Không thể chuyển từ '%s' sang '%s'",
                                    fromState.getStateName(), toState.getStateName())));
            if (!Boolean.TRUE.equals(transition.getIsActive()))
                throw AppException.badRequest("Transition này hiện không khả dụng");
        }

        // Thực hiện chuyển state
        record.setCurrentState(toState);
        AppRecord saved = recordRepository.save(record);

        // Lưu lịch sử
        saveStateHistory(saved, fromState, toState, transition, cmd.getActionCode(), cmd.getNote());

        log.info("Record [{}] transitioned: {} → {}", recordId,
                fromState != null ? fromState.getStateCode() : "null", toState.getStateCode());

        return mapper.toDto(saved);
    }

    @Override
    public List<AvailableTransitionDto> getAvailableTransitions(Long recordId) {
        AppRecord record = recordRepository.findActiveById(recordId)
                .orElseThrow(() -> AppException.notFound("Record", recordId));

        if (record.getCurrentState() == null) return List.of();

        return transitionRepository
                .findAvailableTransitions(record.getCurrentState().getId())
                .stream()
                .map(t -> AvailableTransitionDto.builder()
                        .transitionId(t.getId())
                        .transitionCode(t.getTransitionCode())
                        .transitionName(t.getTransitionName())
                        .actionCode(t.getActionCode())
                        .toStateId(t.getToState().getId())
                        .toStateName(t.getToState().getStateName())
                        .toStateColor(t.getToState().getColorCode())
                        .build())
                .toList();
    }

    @Override
    public List<AppRecordSummaryDto> findChildren(Long parentRecordId) {
        return mapper.toSummaryDtoList(
                recordRepository.findAllByParentRecord_IdAndIsDeletedFalse(parentRecordId));
    }

    @Override
    public List<AppRecordSummaryDto> findByRootRecord(Long rootRecordId) {
        return mapper.toSummaryDtoList(
                recordRepository.findAllByRootRecord_IdAndIsDeletedFalse(rootRecordId));
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private void saveStateHistory(AppRecord record,
                                  DpWorkflowState fromState,
                                  DpWorkflowState toState,
                                  DpWorkflowTransition transition,
                                  String actionCode,
                                  String note) {
        DpWorkflow workflow = toState.getWorkflow();
        AppRecordStateHistory history = AppRecordStateHistory.builder()
                .record(record)
                .workflow(workflow)
                .fromState(fromState)
                .toState(toState)
                .transition(transition)
                .actionCode(actionCode)
                .note(note)
                .changedAt(LocalDateTime.now())
                .build();
        historyRepository.save(history);
    }
}