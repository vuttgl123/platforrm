package com.meta_forge_platform.definition.infrastructure.persistence.view;

import com.meta_forge_platform.definition.domain.model.view.ViewDefinition;
import com.meta_forge_platform.shared.domain.enumtype.ViewType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ViewDefinitionRepository extends JpaRepository<ViewDefinition, Long> {

    Optional<ViewDefinition> findByEntity_IdAndCode(Long entityId, String code);

    boolean existsByEntity_IdAndCode(Long entityId, String code);

    List<ViewDefinition> findByEntity_IdOrderBySortOrderAscIdAsc(Long entityId);

    List<ViewDefinition> findByEntity_IdAndActiveTrueOrderBySortOrderAscIdAsc(Long entityId);

    List<ViewDefinition> findByEntity_IdAndViewTypeOrderBySortOrderAscIdAsc(Long entityId, ViewType viewType);
}
