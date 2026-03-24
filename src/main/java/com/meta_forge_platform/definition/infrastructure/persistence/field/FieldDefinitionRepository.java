package com.meta_forge_platform.definition.infrastructure.persistence.field;

import com.meta_forge_platform.definition.domain.model.field.FieldDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FieldDefinitionRepository extends JpaRepository<FieldDefinition, Long> {

    Optional<FieldDefinition> findByEntity_IdAndCode(Long entityId, String code);

    boolean existsByEntity_IdAndCode(Long entityId, String code);

    List<FieldDefinition> findByEntity_IdOrderBySortOrderAscIdAsc(Long entityId);

    List<FieldDefinition> findByEntity_CodeOrderBySortOrderAscIdAsc(String entityCode);

    List<FieldDefinition> findByEntity_IdAndRequiredTrueOrderBySortOrderAscIdAsc(Long entityId);

    List<FieldDefinition> findByEntity_IdAndSearchableTrueOrderBySortOrderAscIdAsc(Long entityId);

    List<FieldDefinition> findByEntity_IdAndFilterableTrueOrderBySortOrderAscIdAsc(Long entityId);

    List<FieldDefinition> findByEntity_IdAndSortableTrueOrderBySortOrderAscIdAsc(Long entityId);

    List<FieldDefinition> findByEntity_IdAndListableTrueOrderBySortOrderAscIdAsc(Long entityId);

    List<FieldDefinition> findByEntity_IdAndDetailVisibleTrueOrderBySortOrderAscIdAsc(Long entityId);

    List<FieldDefinition> findByEntity_IdAndEditableTrueOrderBySortOrderAscIdAsc(Long entityId);

    List<FieldDefinition> findByEntity_IdAndSystemTrueOrderBySortOrderAscIdAsc(Long entityId);

    List<FieldDefinition> findByRelationEntity_IdOrderByEntity_IdAscSortOrderAscIdAsc(Long relationEntityId);
}
