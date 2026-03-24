package com.meta_forge_platform.definition.infrastructure.persistence.workflow;

import com.meta_forge_platform.definition.domain.model.workflow.WorkflowTransitionDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkflowTransitionDefinitionRepository extends JpaRepository<WorkflowTransitionDefinition, Long> {

    Optional<WorkflowTransitionDefinition> findByWorkflow_IdAndCode(Long workflowId, String code);

    boolean existsByWorkflow_IdAndCode(Long workflowId, String code);

    List<WorkflowTransitionDefinition> findByWorkflow_IdOrderBySortOrderAscIdAsc(Long workflowId);

    List<WorkflowTransitionDefinition> findByWorkflow_IdAndActiveTrueOrderBySortOrderAscIdAsc(Long workflowId);

    List<WorkflowTransitionDefinition> findByWorkflow_IdAndFromState_IdOrderBySortOrderAscIdAsc(Long workflowId, Long fromStateId);

    List<WorkflowTransitionDefinition> findByWorkflow_IdAndFromState_IdAndActiveTrueOrderBySortOrderAscIdAsc(Long workflowId, Long fromStateId);

    List<WorkflowTransitionDefinition> findByFromState_IdAndToState_IdOrderBySortOrderAscIdAsc(Long fromStateId, Long toStateId);
}
