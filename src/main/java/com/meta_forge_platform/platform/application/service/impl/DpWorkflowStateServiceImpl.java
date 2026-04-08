package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.workflowstate.*;
import com.meta_forge_platform.platform.application.mapper.DpWorkflowStateMapper;
import com.meta_forge_platform.platform.application.service.DpWorkflowStateService;
import com.meta_forge_platform.platform.domain.entity.DpWorkflow;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowState;
import com.meta_forge_platform.platform.domain.enumeration.WorkflowStateType;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowStateRepository;
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
public class DpWorkflowStateServiceImpl
        extends BaseServiceImpl<DpWorkflowState, DpWorkflowStateDto, CreateDpWorkflowStateCmd, UpdateDpWorkflowStateCmd, Long>
        implements DpWorkflowStateService {

    private final DpWorkflowStateRepository stateRepository;
    private final DpWorkflowRepository workflowRepository;
    private final DpWorkflowStateMapper mapper;

    public DpWorkflowStateServiceImpl(DpWorkflowStateRepository stateRepository,
                                      DpWorkflowRepository workflowRepository,
                                      DpWorkflowStateMapper mapper) {
        super(stateRepository);
        this.stateRepository = stateRepository;
        this.workflowRepository = workflowRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "DpWorkflowState";
    }

    @Override
    protected DpWorkflowStateDto toDto(DpWorkflowState entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpWorkflowState toEntity(CreateDpWorkflowStateCmd command) {
        DpWorkflow workflow = workflowRepository.findActiveById(command.getWorkflowId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpWorkflow",
                        command.getWorkflowId()
                ));

        DpWorkflowState state = DpWorkflowState.create(
                workflow,
                command.getStateCode(),
                command.getStateName()
        );

        state.applyMetadata(
                command.getStateName(),
                toStateType(command.getStateType()),
                command.getIsInitial(),
                command.getIsFinal(),
                command.getColorCode(),
                command.getSortOrder(),
                command.getConfigJson()
        );

        return state;
    }

    @Override
    protected void updateEntity(DpWorkflowState entity, UpdateDpWorkflowStateCmd command) {
        entity.applyMetadata(
                command.getStateName(),
                toStateType(command.getStateType()),
                command.getIsInitial(),
                command.getIsFinal(),
                command.getColorCode(),
                command.getSortOrder(),
                command.getConfigJson()
        );
    }

    @Override
    protected void validateCreateCommand(CreateDpWorkflowStateCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getWorkflowId(), "workflowId");
        validateNotNull(command.getStateCode(), "stateCode");
        validateNotNull(command.getStateName(), "stateName");
        validateNotNull(command.getStateType(), "stateType");
        validateNotNull(command.getIsInitial(), "isInitial");
        validateNotNull(command.getIsFinal(), "isFinal");
        validateNotNull(command.getSortOrder(), "sortOrder");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpWorkflowStateCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getStateName(), "stateName");
        validateNotNull(command.getStateType(), "stateType");
        validateNotNull(command.getIsInitial(), "isInitial");
        validateNotNull(command.getIsFinal(), "isFinal");
        validateNotNull(command.getSortOrder(), "sortOrder");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpWorkflowStateCmd command) {
        if (stateRepository.existsByWorkflow_IdAndStateCodeAndIsDeletedFalse(
                command.getWorkflowId(),
                command.getStateCode()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpWorkflowState",
                    command.getStateCode()
            );
        }

        if (Boolean.TRUE.equals(command.getIsInitial())
                && stateRepository.existsOtherInitialState(command.getWorkflowId(), 0L)) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "initialStateAlreadyExists", command.getWorkflowId());
        }
    }

    @Override
    protected void beforeUpdate(DpWorkflowState entity, UpdateDpWorkflowStateCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpWorkflowState",
                    entity.getId()
            );
        }

        if (Boolean.TRUE.equals(command.getIsInitial())
                && stateRepository.existsOtherInitialState(entity.getWorkflow().getId(), entity.getId())) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "initialStateAlreadyExists", entity.getWorkflow().getId());
        }
    }

    @Override
    protected void beforeDelete(DpWorkflowState entity) {
        if (Boolean.TRUE.equals(entity.getIsInitial())) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "cannotDeleteInitialState", entity.getId());
        }

        if (stateRepository.isStateInUse(entity.getId())) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "stateInUse", entity.getId());
        }
    }

    @Override
    protected Specification<DpWorkflowState> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("stateCode")), p),
                cb.like(cb.lower(root.get("stateName")), p)
        );
    }

    @Override
    public List<DpWorkflowStateSummaryDto> findAllByWorkflow(Long workflowId) {
        return mapper.toSummaryDtoList(
                stateRepository.findAllByWorkflow_IdAndIsDeletedFalseOrderBySortOrderAsc(workflowId)
        );
    }

    @Override
    public DpWorkflowStateSummaryDto getInitialState(Long workflowId) {
        return stateRepository.findByWorkflow_IdAndIsInitialTrueAndIsDeletedFalse(workflowId)
                .map(mapper::toSummaryDto)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "InitialWorkflowState",
                        workflowId
                ));
    }

    private WorkflowStateType toStateType(String raw) {
        try {
            return WorkflowStateType.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "stateType", raw);
        }
    }
}