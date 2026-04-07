package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpEntityConstraint;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DpEntityConstraintRepository extends BaseRepository<DpEntityConstraint, Long> {

    boolean existsByEntity_IdAndConstraintCodeAndIsDeletedFalse(Long entityId, String constraintCode);

    boolean existsByEntity_IdAndConstraintCodeAndIdNotAndIsDeletedFalse(Long entityId, String constraintCode, Long excludeId);

    List<DpEntityConstraint> findAllByEntity_IdAndIsDeletedFalse(Long entityId);

    List<DpEntityConstraint> findAllByEntity_IdAndIsActiveTrueAndIsDeletedFalse(Long entityId);
}