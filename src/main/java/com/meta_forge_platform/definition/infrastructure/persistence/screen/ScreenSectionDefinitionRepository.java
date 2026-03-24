package com.meta_forge_platform.definition.infrastructure.persistence.screen;

import com.meta_forge_platform.definition.domain.model.screen.ScreenSectionDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenSectionDefinitionRepository extends JpaRepository<ScreenSectionDefinition, Long> {

    Optional<ScreenSectionDefinition> findByScreen_IdAndCode(Long screenId, String code);

    boolean existsByScreen_IdAndCode(Long screenId, String code);

    List<ScreenSectionDefinition> findByScreen_IdOrderBySortOrderAscIdAsc(Long screenId);
}
