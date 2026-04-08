package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.workflow.*;
import com.meta_forge_platform.platform.application.mapper.DpWorkflowMapper;
import com.meta_forge_platform.platform.application.service.DpWorkflowService;
import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.platform.domain.entity.DpWorkflow;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowRepository;
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
public class DpWorkflowServiceImpl
        extends BaseServiceImpl<DpWorkflow, DpWorkflowDto, CreateDpWorkflowCmd, UpdateDpWorkflowCmd, Long>
        implements DpWorkflowService {

    private final DpWorkflowRepository workflowRepository;
    private final DpEntityRepository entityRepository;
    private final DpWorkflowMapper mapper;

    public DpWorkflowServiceImpl(DpWorkflowRepository workflowRepository,
                                 DpEntityRepository entityRepository,
                                 DpWorkflowMapper mapper) {
        super(workflowRepository);
        this.workflowRepository = workflowRepository;
        this.entityRepository = entityRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getEntityName() {
        return "DpWorkflow";
    }

    @Override
    protected DpWorkflowDto toDto(DpWorkflow entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected DpWorkflow toEntity(CreateDpWorkflowCmd command) {
        DpEntity entity = entityRepository.findActiveById(command.getEntityId())
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DpEntity",
                        command.getEntityId()
                ));

        DpWorkflow workflow = DpWorkflow.create(
                entity,
                command.getWorkflowCode(),
                command.getWorkflowName(),
                command.getDescription()
        );

        workflow.applyMetadata(
                command.getWorkflowName(),
                command.getDescription(),
                command.getIsDefault(),
                command.getIsActive(),
                command.getConfigJson()
        );

        return workflow;
    }

    @Override
    protected void updateEntity(DpWorkflow entity, UpdateDpWorkflowCmd command) {
        entity.applyMetadata(
                command.getWorkflowName(),
                command.getDescription(),
                command.getIsDefault(),
                command.getIsActive(),
                command.getConfigJson()
        );
    }

    @Override
    protected void validateCreateCommand(CreateDpWorkflowCmd command) {
        super.validateCreateCommand(command);
        validateNotNull(command.getEntityId(), "entityId");
        validateNotNull(command.getWorkflowCode(), "workflowCode");
        validateNotNull(command.getWorkflowName(), "workflowName");
        validateNotNull(command.getIsDefault(), "isDefault");
        validateNotNull(command.getIsActive(), "isActive");
    }

    @Override
    protected void validateUpdateCommand(UpdateDpWorkflowCmd command) {
        super.validateUpdateCommand(command);
        validateNotNull(command.getWorkflowName(), "workflowName");
        validateNotNull(command.getIsDefault(), "isDefault");
        validateNotNull(command.getIsActive(), "isActive");
        validateNotNull(command.getVersionNo(), "versionNo");
    }

    @Override
    protected void beforeCreate(CreateDpWorkflowCmd command) {
        if (workflowRepository.existsByEntity_IdAndWorkflowCodeAndIsDeletedFalse(
                command.getEntityId(),
                command.getWorkflowCode()
        )) {
            throw AppException.of(
                    ErrorCode.RECORD_DUPLICATE,
                    "DpWorkflow",
                    command.getWorkflowCode()
            );
        }

        if (Boolean.TRUE.equals(command.getIsDefault())) {
            workflowRepository.unsetAllDefaultByEntityId(command.getEntityId());
        }
    }

    @Override
    protected void beforeUpdate(DpWorkflow entity, UpdateDpWorkflowCmd command) {
        if (!Objects.equals(entity.getVersionNo(), command.getVersionNo())) {
            throw AppException.of(
                    ErrorCode.OPTIMISTIC_LOCK,
                    "DpWorkflow",
                    entity.getId()
            );
        }

        if (Boolean.TRUE.equals(command.getIsDefault())) {
            workflowRepository.unsetDefaultExcept(entity.getEntity().getId(), entity.getId());
        }
    }

    @Override
    protected Specification<DpWorkflow> buildKeywordSpec(String keyword) {
        String p = "%" + keyword.toLowerCase(Locale.ROOT) + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("workflowCode")), p),
                cb.like(cb.lower(root.get("workflowName")), p)
        );
    }

    @Override
    public List<DpWorkflowSummaryDto> findAllByEntity(Long entityId) {
        return mapper.toSummaryDtoList(
                workflowRepository.findAllByEntity_IdAndIsDeletedFalseOrderByWorkflowNameAsc(entityId)
        );
    }

    @Override
    public DpWorkflowDto getDefaultByEntity(Long entityId) {
        return workflowRepository.findByEntity_IdAndIsDefaultTrueAndIsDeletedFalse(entityId)
                .map(mapper::toDto)
                .orElseThrow(() -> AppException.of(
                        ErrorCode.ENTITY_NOT_FOUND,
                        "DefaultWorkflow",
                        entityId
                ));
    }
}