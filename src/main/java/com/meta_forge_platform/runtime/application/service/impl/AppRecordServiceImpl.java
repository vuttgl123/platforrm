package com.meta_forge_platform.runtime.application.service.impl;

import com.meta_forge_platform.platform.domain.entity.DpEntity;
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
import com.meta_forge_platform.runtime.domain.enumeration.RecordStatus;
import com.meta_forge_platform.runtime.infrastructure.repository.AppRecordRepository;
import com.meta_forge_platform.runtime.infrastructure.repository.AppRecordStateHistoryRepository;
import com.meta_forge_platform.shared.application.BaseServiceImpl;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

    @Override
    protected String getEntityName() {
        return "AppRecord";
    }

    @Override
    protected AppRecordDto toDto(AppRecord entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected AppRecord toEntity(CreateAppRecordCmd command) {
        DpEntity entity = entityRepository.findActiveById(command.getEntityId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpEntity",
                        command.getEntityId()
                ));

        AppRecord record = AppRecord.create(
                entity,
                command.getRecordCode(),
                command.getDataJson()
        );

        record.applyMetadata(
                command.getDisplayName(),
                toStatus(command.getStatus()),
                command.getDataJson()
        );

        AppRecord parent = resolveRecord(command.getParentRecordId(), "ParentRecord");
        AppRecord root = resolveRecord(command.getRootRecordId(), "RootRecord");
        record.assignParent(parent, root);

        workflowRepository.findByEntity_IdAndIsDefaultTrueAndIsDeletedFalse(command.getEntityId())
                .flatMap(wf -> stateRepository.findByWorkflow_IdAndIsInitialTrueAndIsDeletedFalse(wf.getId()))
                .ifPresent(record::assignWorkflowState);

        return record;
    }

    @Override
    protected void updateEntity(AppRecord entity, UpdateAppRecordCmd command) {
        entity.applyMetadata(
                command.getDisplayName(),
                toStatus(command.getStatus()),
                command.getDataJson()
        );
    }

    @Override
    protected void validateCreateCommand(CreateAppRecordCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getEntityId(), "entityId");
        validateNotNull(command.getStatus(), "status");
    }

    @Override
    protected void validateUpdateCommand(UpdateAppRecordCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getStatus(), "status");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateAppRecordCmd command) {
        if (command.getRecordCode() != null
                && !command.getRecordCode().isBlank()
                && recordRepository.existsByEntity_IdAndRecordCodeAndIsDeletedFalse(
                command.getEntityId(),
                command.getRecordCode()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "AppRecord",
                    command.getRecordCode()
            );
        }
    }

    @Override
    protected void beforeUpdate(AppRecord entity, UpdateAppRecordCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "AppRecord",
                    entity.getId()
            );
        }
    }

    @Override
    protected void afterCreate(AppRecord entity, CreateAppRecordCmd command) {
        if (entity.getCurrentState() != null) {
            saveStateHistory(entity, null, entity.getCurrentState(), null, "CREATE", null);
        }
    }

    @Override
    protected Specification<AppRecord> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(cb.coalesce(root.get("recordCode"), "")), p),
                cb.like(cb.lower(cb.coalesce(root.get("displayName"), "")), p)
        );
    }

    @Override
    @Transactional
    public AppRecordDto transition(Long recordId, TransitionAppRecordCmd command) {
        AppRecord record = recordRepository.findActiveById(recordId)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "AppRecord",
                        recordId
                ));

        if (!Objects.equals(record.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(ErrorCode.OPTIMISTIC_LOCK, "AppRecord", recordId);
        }

        DpWorkflowState fromState = record.getCurrentState();

        DpWorkflowState toState = stateRepository.findActiveById(command.getToStateId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpWorkflowState",
                        command.getToStateId()
                ));

        DpWorkflowTransition transition = null;
        if (fromState != null) {
            transition = transitionRepository.findAvailableTransitions(fromState.getId())
                    .stream()
                    .filter(t -> Objects.equals(t.getToState().getId(), toState.getId()))
                    .findFirst()
                    .orElseThrow(() -> AppException.of(
                            ErrorCode.BAD_REQUEST,
                            "transition",
                            fromState.getId() + "->" + toState.getId()
                    ));
        }

        record.transitionTo(toState);
        AppRecord saved = recordRepository.save(record);

        saveStateHistory(saved, fromState, toState, transition, command.getActionCode(), command.getNote());

        return mapper.toDto(saved);
    }

    @Override
    public List<AvailableTransitionDto> getAvailableTransitions(Long recordId) {
        AppRecord record = recordRepository.findActiveById(recordId)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "AppRecord",
                        recordId
                ));

        if (record.getCurrentState() == null) {
            return List.of();
        }

        return transitionRepository.findAvailableTransitions(record.getCurrentState().getId())
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
                recordRepository.findAllByParentRecord_IdAndIsDeletedFalse(parentRecordId)
        );
    }

    @Override
    public List<AppRecordSummaryDto> findByRootRecord(Long rootRecordId) {
        return mapper.toSummaryDtoList(
                recordRepository.findAllByRootRecord_IdAndIsDeletedFalse(rootRecordId)
        );
    }

    private AppRecord resolveRecord(Long id, String label) {
        if (id == null) {
            return null;
        }
        return recordRepository.findActiveById(id)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        label,
                        id
                ));
    }

    private RecordStatus toStatus(String raw) {
        try {
            return RecordStatus.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "status", raw);
        }
    }

    private void saveStateHistory(AppRecord record,
                                  DpWorkflowState fromState,
                                  DpWorkflowState toState,
                                  DpWorkflowTransition transition,
                                  String actionCode,
                                  String note) {
        AppRecordStateHistory history = AppRecordStateHistory.create(
                record,
                toState.getWorkflow(),
                fromState,
                toState,
                transition,
                actionCode,
                note
        );
        historyRepository.save(history);
    }
}