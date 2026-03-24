package com.meta_forge_platform.definition.infrastructure.persistence.workflow;

import com.meta_forge_platform.definition.domain.model.workflow.WorkflowStateDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkflowStateDefinitionRepository extends JpaRepository<WorkflowStateDefinition, Long> {

    Optional<WorkflowStateDefinition> findByWorkflow_IdAndCode(Long workflowId, String code);

    boolean existsByWorkflow_IdAndCode(Long workflowId, String code);

    List<WorkflowStateDefinition> findByWorkflow_IdOrderBySortOrderAscIdAsc(Long workflowId);

    List<WorkflowStateDefinition> findByWorkflow_IdAndInitialTrueOrderBySortOrderAscIdAsc(Long workflowId);

    List<WorkflowStateDefinition> findByWorkflow_IdAndFinalStateTrueOrderBySortOrderAscIdAsc(Long workflowId);
}