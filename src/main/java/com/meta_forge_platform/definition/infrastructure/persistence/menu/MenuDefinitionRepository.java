package com.meta_forge_platform.definition.infrastructure.persistence.menu;

import com.meta_forge_platform.definition.domain.model.menu.MenuDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuDefinitionRepository extends JpaRepository<MenuDefinition, Long> {

    Optional<MenuDefinition> findByModule_IdAndCode(Long moduleId, String code);

    boolean existsByModule_IdAndCode(Long moduleId, String code);

    List<MenuDefinition> findByModule_IdOrderBySortOrderAscIdAsc(Long moduleId);

    List<MenuDefinition> findByModule_IdAndActiveTrueOrderBySortOrderAscIdAsc(Long moduleId);

    List<MenuDefinition> findByParent_IdOrderBySortOrderAscIdAsc(Long parentId);

    List<MenuDefinition> findByModule_IdAndParentIsNullOrderBySortOrderAscIdAsc(Long moduleId);

    List<MenuDefinition> findByScreen_IdOrderBySortOrderAscIdAsc(Long screenId);
}
