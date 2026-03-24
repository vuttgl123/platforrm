package com.meta_forge_platform.definition.infrastructure.persistence.screen;

import com.meta_forge_platform.definition.domain.model.screen.ScreenDefinition;
import com.meta_forge_platform.shared.domain.enumtype.ScreenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenDefinitionRepository extends JpaRepository<ScreenDefinition, Long> {

    Optional<ScreenDefinition> findByEntity_IdAndCode(Long entityId, String code);

    boolean existsByEntity_IdAndCode(Long entityId, String code);

    List<ScreenDefinition> findByEntity_IdOrderBySortOrderAscIdAsc(Long entityId);

    List<ScreenDefinition> findByEntity_IdAndActiveTrueOrderBySortOrderAscIdAsc(Long entityId);

    List<ScreenDefinition> findByEntity_IdAndScreenTypeOrderBySortOrderAscIdAsc(Long entityId, ScreenType screenType);

    Optional<ScreenDefinition> findByEntity_CodeAndCode(String entityCode, String code);
}
