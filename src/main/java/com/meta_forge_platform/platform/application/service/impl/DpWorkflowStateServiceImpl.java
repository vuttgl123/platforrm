package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.workflowstate.*;
import com.meta_forge_platform.platform.application.mapper.DpWorkflowStateMapper;
import com.meta_forge_platform.platform.application.service.DpWorkflowStateService;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowState;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowStateRepository;
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

    @Override protected DpWorkflowStateDto toDto(DpWorkflowState e) { return mapper.toDto(e); }
    @Override protected DpWorkflowState toEntity(CreateDpWorkflowStateCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(DpWorkflowState e, UpdateDpWorkflowStateCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void afterToEntity(DpWorkflowState entity, CreateDpWorkflowStateCmd cmd) {
        entity.setWorkflow(workflowRepository.findActiveById(cmd.getWorkflowId())
                .orElseThrow(() -> AppException.notFound("Workflow", cmd.getWorkflowId())));
    }

    @Override
    protected void beforeCreate(CreateDpWorkflowStateCmd cmd) {
        if (stateRepository.existsByWorkflow_IdAndStateCodeAndIsDeletedFalse(
                cmd.getWorkflowId(), cmd.getStateCode()))
            throw AppException.conflict("State code đã tồn tại: " + cmd.getStateCode());
        // Mỗi workflow chỉ có một initial state
        if (Boolean.TRUE.equals(cmd.getIsInitial()) &&
                stateRepository.existsByWorkflow_IdAndIsInitialTrueAndIdNotAndIsDeletedFalse(
                        cmd.getWorkflowId(), 0L))
            throw AppException.badRequest("Workflow đã có initial state, chỉ được có một");
    }

    @Override
    protected Specification<DpWorkflowState> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("stateCode")), p),
                cb.like(cb.lower(root.get("stateName")), p)
        );
    }

    @Override
    public List<DpWorkflowStateSummaryDto> findAllByWorkflow(Long workflowId) {
        return mapper.toSummaryDtoList(
                stateRepository.findAllByWorkflow_IdAndIsDeletedFalseOrderBySortOrderAsc(workflowId));
    }

    @Override
    public DpWorkflowStateSummaryDto getInitialState(Long workflowId) {
        return stateRepository.findByWorkflow_IdAndIsInitialTrueAndIsDeletedFalse(workflowId)
                .map(mapper::toSummaryDto)
                .orElseThrow(() -> AppException.notFound("InitialState cho Workflow", workflowId));
    }
}