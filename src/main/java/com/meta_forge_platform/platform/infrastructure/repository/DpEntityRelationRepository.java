package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpEntityRelation;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DpEntityRelationRepository extends BaseRepository<DpEntityRelation, Long> {

    boolean existsBySourceEntity_IdAndRelationCodeAndIsDeletedFalse(Long sourceEntityId, String relationCode);

    List<DpEntityRelation> findAllBySourceEntity_IdAndIsDeletedFalse(Long sourceEntityId);

    List<DpEntityRelation> findAllByTargetEntity_IdAndIsDeletedFalse(Long targetEntityId);

    List<DpEntityRelation> findAllBySourceEntity_IdAndIsActiveTrueAndIsDeletedFalse(Long sourceEntityId);
}