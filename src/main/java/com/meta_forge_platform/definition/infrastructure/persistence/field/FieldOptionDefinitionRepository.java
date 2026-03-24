package com.meta_forge_platform.definition.infrastructure.persistence.field;

import com.meta_forge_platform.definition.domain.model.field.FieldOptionDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FieldOptionDefinitionRepository extends JpaRepository<FieldOptionDefinition, Long> {

    Optional<FieldOptionDefinition> findByField_IdAndCode(Long fieldId, String code);

    boolean existsByField_IdAndCode(Long fieldId, String code);

    List<FieldOptionDefinition> findByField_IdOrderBySortOrderAscIdAsc(Long fieldId);

    List<FieldOptionDefinition> findByField_IdAndActiveTrueOrderBySortOrderAscIdAsc(Long fieldId);

    List<FieldOptionDefinition> findByField_IdAndDefaultOptionTrueOrderBySortOrderAscIdAsc(Long fieldId);

    List<FieldOptionDefinition> findByField_Entity_IdOrderByField_IdAscSortOrderAscIdAsc(Long entityId);
}
