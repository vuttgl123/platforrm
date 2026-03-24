package com.meta_forge_platform.definition.infrastructure.persistence.entity;

import com.meta_forge_platform.definition.domain.model.entity.EntityRelationDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntityRelationDefinitionRepository extends JpaRepository<EntityRelationDefinition, Long> {

    Optional<EntityRelationDefinition> findBySourceEntity_IdAndCode(Long sourceEntityId, String code);

    boolean existsBySourceEntity_IdAndCode(Long sourceEntityId, String code);

    List<EntityRelationDefinition> findBySourceEntity_IdOrderByIdAsc(Long sourceEntityId);

    List<EntityRelationDefinition> findByTargetEntity_IdOrderByIdAsc(Long targetEntityId);

    List<EntityRelationDefinition> findBySourceEntity_IdAndActiveTrueOrderByIdAsc(Long sourceEntityId);

    List<EntityRelationDefinition> findByTargetEntity_IdAndActiveTrueOrderByIdAsc(Long targetEntityId);
}
