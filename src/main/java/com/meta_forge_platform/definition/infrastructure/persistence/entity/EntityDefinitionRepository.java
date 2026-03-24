package com.meta_forge_platform.definition.infrastructure.persistence.entity;

import com.meta_forge_platform.definition.domain.model.entity.EntityDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntityDefinitionRepository extends JpaRepository<EntityDefinition, Long> {

    Optional<EntityDefinition> findByModule_IdAndCode(Long moduleId, String code);

    Optional<EntityDefinition> findByCode(String code);

    boolean existsByModule_IdAndCode(Long moduleId, String code);

    List<EntityDefinition> findByModule_IdOrderByIdAsc(Long moduleId);

    List<EntityDefinition> findByModule_CodeOrderByIdAsc(String moduleCode);

    List<EntityDefinition> findByActiveTrueOrderByIdAsc();

    List<EntityDefinition> findByModule_IdAndActiveTrueOrderByIdAsc(Long moduleId);
}
