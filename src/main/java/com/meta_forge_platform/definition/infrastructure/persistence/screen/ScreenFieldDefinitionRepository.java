package com.meta_forge_platform.definition.infrastructure.persistence.screen;

import com.meta_forge_platform.definition.domain.model.screen.ScreenFieldDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenFieldDefinitionRepository extends JpaRepository<ScreenFieldDefinition, Long> {

    Optional<ScreenFieldDefinition> findBySection_IdAndField_Id(Long sectionId, Long fieldId);

    boolean existsBySection_IdAndField_Id(Long sectionId, Long fieldId);

    List<ScreenFieldDefinition> findBySection_IdOrderBySortOrderAscIdAsc(Long sectionId);

    List<ScreenFieldDefinition> findBySection_Screen_IdOrderBySection_SortOrderAscSection_IdAscSortOrderAscIdAsc(Long screenId);

    List<ScreenFieldDefinition> findByField_IdOrderBySection_IdAscSortOrderAscIdAsc(Long fieldId);
}
