package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.workflow.*;
import com.meta_forge_platform.platform.application.mapper.DpWorkflowMapper;
import com.meta_forge_platform.platform.application.service.DpWorkflowService;
import com.meta_forge_platform.platform.domain.entity.DpWorkflow;
import com.meta_forge_platform.platform.infrastructure.repository.DpEntityRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowRepository;
import com.meta_forge_platform.shared.application.BaseServiceImpl;
import com.meta_forge_platform.shared.domain.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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

    @Override protected DpWorkflowDto toDto(DpWorkflow e) { return mapper.toDto(e); }
    @Override protected DpWorkflow toEntity(CreateDpWorkflowCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(DpWorkflow e, UpdateDpWorkflowCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void afterToEntity(DpWorkflow entity, CreateDpWorkflowCmd cmd) {
        entity.setEntity(entityRepository.findActiveById(cmd.getEntityId())
                .orElseThrow(() -> AppException.notFound("Entity", cmd.getEntityId())));
    }

    @Override
    protected void beforeCreate(CreateDpWorkflowCmd cmd) {
        if (workflowRepository.existsByEntity_IdAndWorkflowCodeAndIsDeletedFalse(
                cmd.getEntityId(), cmd.getWorkflowCode()))
            throw AppException.conflict("Workflow code đã tồn tại: " + cmd.getWorkflowCode());
    }

    @Override
    protected Specification<DpWorkflow> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("workflowCode")), p),
                cb.like(cb.lower(root.get("workflowName")), p)
        );
    }

    @Override
    public List<DpWorkflowSummaryDto> findAllByEntity(Long entityId) {
        return mapper.toSummaryDtoList(
                workflowRepository.findAllByEntity_IdAndIsDeletedFalse(entityId));
    }

    @Override
    public DpWorkflowDto getDefaultByEntity(Long entityId) {
        return workflowRepository.findByEntity_IdAndIsDefaultTrueAndIsDeletedFalse(entityId)
                .map(mapper::toDto)
                .orElseThrow(() -> AppException.notFound("DefaultWorkflow cho Entity", entityId));
    }
}