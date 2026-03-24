package com.meta_forge_platform.definition.infrastructure.persistence.field;

import com.meta_forge_platform.definition.domain.model.field.FieldGroupDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FieldGroupDefinitionRepository extends JpaRepository<FieldGroupDefinition, Long> {

    Optional<FieldGroupDefinition> findByEntity_IdAndCode(Long entityId, String code);

    boolean existsByEntity_IdAndCode(Long entityId, String code);

    List<FieldGroupDefinition> findByEntity_IdOrderBySortOrderAscIdAsc(Long entityId);

    List<FieldGroupDefinition> findByEntity_CodeOrderBySortOrderAscIdAsc(String entityCode);
}
