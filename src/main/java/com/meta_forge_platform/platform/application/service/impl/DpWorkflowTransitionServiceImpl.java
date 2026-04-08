package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.workflowtransition.*;
import com.meta_forge_platform.platform.application.mapper.DpWorkflowTransitionMapper;
import com.meta_forge_platform.platform.application.service.DpWorkflowTransitionService;
import com.meta_forge_platform.platform.domain.entity.DpWorkflow;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowState;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowTransition;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowStateRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowTransitionRepository;
import com.meta_forge_platform.shared.application.BaseServiceImpl;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
public class DpWorkflowTransitionServiceImpl
        extends BaseServiceImpl<DpWorkflowTransition, DpWorkflowTransitionDto, CreateDpWorkflowTransitionCmd, UpdateDpWorkflowTransitionCmd, Long>
        implements DpWorkflowTransitionService {

    private final DpWorkflowTransitionRepository transitionRepository;
    private final DpWorkflowRepository workflowRepository;
    private final DpWorkflowStateRepository stateRepository;
    private final DpWorkflowTransitionMapper mapper;

    public DpWorkflowTransitionServiceImpl(DpWorkflowTransitionRepository transitionRepository,
                                           DpWorkflowRepository workflowRepository,
                                           DpWorkflowStateRepository stateRepository,
                                           DpWorkflowTransitionMapper mapper) {
        super(transitionRepository);
        this.transitionRepository = transitionRepository;
        this.workflowRepository = workflowRepository;
        this.stateRepository = stateRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "DpWorkflowTransition";
    }

    @Override
    protected DpWorkflowTransitionDto toDto(DpWorkflowTransition entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpWorkflowTransition toEntity(CreateDpWorkflowTransitionCmd command) {
        DpWorkflow workflow = workflowRepository.findActiveById(command.getWorkflowId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpWorkflow",
                        command.getWorkflowId()
                ));

        DpWorkflowState fromState = stateRepository.findActiveById(command.getFromStateId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpWorkflowState",
                        command.getFromStateId()
                ));

        DpWorkflowState toState = stateRepository.findActiveById(command.getToStateId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpWorkflowState",
                        command.getToStateId()
                ));

        validateStateBelongsToWorkflow(workflow.getId(), fromState, "fromStateId");
        validateStateBelongsToWorkflow(workflow.getId(), toState, "toStateId");

        DpWorkflowTransition transition = DpWorkflowTransition.create(
                workflow,
                command.getTransitionCode(),
                command.getTransitionName(),
                fromState,
                toState
        );

        transition.applyMetadata(
                command.getTransitionName(),
                fromState,
                toState,
                command.getActionCode(),
                command.getConditionJson(),
                command.getEffectJson(),
                command.getIsActive(),
                command.getSortOrder()
        );

        return transition;
    }

    @Override
    protected void updateEntity(DpWorkflowTransition entity, UpdateDpWorkflowTransitionCmd command) {
        DpWorkflowState fromState = stateRepository.findActiveById(command.getFromStateId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpWorkflowState",
                        command.getFromStateId()
                ));

        DpWorkflowState toState = stateRepository.findActiveById(command.getToStateId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpWorkflowState",
                        command.getToStateId()
                ));

        validateStateBelongsToWorkflow(entity.getWorkflow().getId(), fromState, "fromStateId");
        validateStateBelongsToWorkflow(entity.getWorkflow().getId(), toState, "toStateId");

        entity.applyMetadata(
                command.getTransitionName(),
                fromState,
                toState,
                command.getActionCode(),
                command.getConditionJson(),
                command.getEffectJson(),
                command.getIsActive(),
                command.getSortOrder()
        );
    }

    @Override
    protected void validateCreateCommand(CreateDpWorkflowTransitionCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getWorkflowId(), "workflowId");
        validateNotNull(command.getTransitionCode(), "transitionCode");
        validateNotNull(command.getTransitionName(), "transitionName");
        validateNotNull(command.getFromStateId(), "fromStateId");
        validateNotNull(command.getToStateId(), "toStateId");
        validateNotNull(command.getIsActive(), "isActive");
        validateNotNull(command.getSortOrder(), "sortOrder");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpWorkflowTransitionCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getTransitionName(), "transitionName");
        validateNotNull(command.getFromStateId(), "fromStateId");
        validateNotNull(command.getToStateId(), "toStateId");
        validateNotNull(command.getIsActive(), "isActive");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpWorkflowTransitionCmd command) {
        if (transitionRepository.existsByWorkflow_IdAndTransitionCodeAndIsDeletedFalse(
                command.getWorkflowId(),
                command.getTransitionCode()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpWorkflowTransition",
                    command.getTransitionCode()
            );
        }
    }

    @Override
    protected void beforeUpdate(DpWorkflowTransition entity, UpdateDpWorkflowTransitionCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpWorkflowTransition",
                    entity.getId()
            );
        }
    }

    @Override
    protected Specification<DpWorkflowTransition> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("transitionCode")), p),
                cb.like(cb.lower(root.get("transitionName")), p),
                cb.like(cb.lower(cb.coalesce(root.get("actionCode"), "")), p)
        );
    }

    @Override
    public List<DpWorkflowTransitionSummaryDto> findAllByWorkflow(Long workflowId) {
        return mapper.toSummaryDtoList(
                transitionRepository.findAllByWorkflow_IdAndIsDeletedFalseOrderBySortOrderAsc(workflowId)
        );
    }

    @Override
    public List<DpWorkflowTransitionSummaryDto> findAvailableTransitions(Long fromStateId) {
        return mapper.toSummaryDtoList(
                transitionRepository.findAvailableTransitions(fromStateId)
        );
    }

    private void validateStateBelongsToWorkflow(Long workflowId, DpWorkflowState state, String fieldName) {
        if (state.getWorkflow() == null || !Objects.equals(state.getWorkflow().getId(), workflowId)) {
            throw AppException.of(ErrorCode.BAD_REQUEST, fieldName, state.getId());
        }
    }
}