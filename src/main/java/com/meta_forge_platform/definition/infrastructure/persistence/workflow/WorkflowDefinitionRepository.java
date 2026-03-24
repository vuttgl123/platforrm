package com.meta_forge_platform.definition.infrastructure.persistence.workflow;

import com.meta_forge_platform.definition.domain.model.workflow.WorkflowDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkflowDefinitionRepository extends JpaRepository<WorkflowDefinition, Long> {

    Optional<WorkflowDefinition> findByEntity_IdAndCode(Long entityId, String code);

    boolean existsByEntity_IdAndCode(Long entityId, String code);

    List<WorkflowDefinition> findByEntity_IdOrderByIdAsc(Long entityId);

    List<WorkflowDefinition> findByEntity_IdAndActiveTrueOrderByIdAsc(Long entityId);

    Optional<WorkflowDefinition> findByEntity_CodeAndCode(String entityCode, String code);
}
