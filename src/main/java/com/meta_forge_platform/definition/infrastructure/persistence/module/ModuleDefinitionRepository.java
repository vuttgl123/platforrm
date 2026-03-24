package com.meta_forge_platform.definition.infrastructure.persistence.module;

import com.meta_forge_platform.definition.domain.model.module.ModuleDefinition;
import com.meta_forge_platform.shared.domain.constant.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModuleDefinitionRepository extends JpaRepository<ModuleDefinition, Long> {

    Optional<ModuleDefinition> findByCode(String code);

    boolean existsByCode(String code);

    List<ModuleDefinition> findAllByOrderBySortOrderAscIdAsc();

    List<ModuleDefinition> findByStatusOrderBySortOrderAscIdAsc(CommonStatus status);
}
