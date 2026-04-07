package com.meta_forge_platform.platform.application.service.impl;

import com.meta_forge_platform.platform.application.dto.workflowtransition.*;
import com.meta_forge_platform.platform.application.mapper.DpWorkflowTransitionMapper;
import com.meta_forge_platform.platform.application.service.DpWorkflowTransitionService;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowTransition;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowStateRepository;
import com.meta_forge_platform.platform.infrastructure.repository.DpWorkflowTransitionRepository;
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

    @Override protected DpWorkflowTransitionDto toDto(DpWorkflowTransition e) { return mapper.toDto(e); }
    @Override protected DpWorkflowTransition toEntity(CreateDpWorkflowTransitionCmd c) { return mapper.toEntity(c); }
    @Override protected void updateEntity(DpWorkflowTransition e, UpdateDpWorkflowTransitionCmd c) { mapper.updateEntity(e, c); }

    @Override
    protected void afterToEntity(DpWorkflowTransition entity, CreateDpWorkflowTransitionCmd cmd) {
        entity.setWorkflow(workflowRepository.findActiveById(cmd.getWorkflowId())
                .orElseThrow(() -> AppException.notFound("Workflow", cmd.getWorkflowId())));
        entity.setFromState(stateRepository.findActiveById(cmd.getFromStateId())
                .orElseThrow(() -> AppException.notFound("FromState", cmd.getFromStateId())));
        entity.setToState(stateRepository.findActiveById(cmd.getToStateId())
                .orElseThrow(() -> AppException.notFound("ToState", cmd.getToStateId())));
    }

    @Override
    protected void beforeCreate(CreateDpWorkflowTransitionCmd cmd) {
        if (transitionRepository.existsByWorkflow_IdAndTransitionCodeAndIsDeletedFalse(
                cmd.getWorkflowId(), cmd.getTransitionCode()))
            throw AppException.conflict("Transition code đã tồn tại: " + cmd.getTransitionCode());
        // fromState và toState phải thuộc cùng workflow
        stateRepository.findActiveById(cmd.getFromStateId()).ifPresent(from -> {
            if (!from.getWorkflow().getId().equals(cmd.getWorkflowId()))
                throw AppException.badRequest("FromState không thuộc workflow này");
        });
        stateRepository.findActiveById(cmd.getToStateId()).ifPresent(to -> {
            if (!to.getWorkflow().getId().equals(cmd.getWorkflowId()))
                throw AppException.badRequest("ToState không thuộc workflow này");
        });
    }

    @Override
    protected Specification<DpWorkflowTransition> buildKeywordSpec(String kw) {
        String p = "%" + kw.toLowerCase() + "%";
        return (root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("transitionCode")), p),
                cb.like(cb.lower(root.get("transitionName")), p),
                cb.like(cb.lower(root.get("actionCode")), p)
        );
    }

    @Override
    public List<DpWorkflowTransitionSummaryDto> findAllByWorkflow(Long workflowId) {
        return mapper.toSummaryDtoList(
                transitionRepository.findAllByWorkflow_IdAndIsDeletedFalseOrderBySortOrderAsc(workflowId));
    }

    @Override
    public List<DpWorkflowTransitionSummaryDto> findAvailableTransitions(Long fromStateId) {
        return mapper.toSummaryDtoList(
                transitionRepository.findAvailableTransitions(fromStateId));
    }
}