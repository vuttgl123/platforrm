package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpEntityConstraint;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpEntityConstraintRepository extends BaseRepository<DpEntityConstraint, Long> {

    Optional<DpEntityConstraint> findByEntity_IdAndConstraintCodeAndIsDeletedFalse(
            Long entityId, String constraintCode);

    // ── Tìm theo entity ───────────────────────────────────────────────────────

    List<DpEntityConstraint> findAllByEntity_IdAndIsDeletedFalse(Long entityId);

    List<DpEntityConstraint> findAllByEntity_IdAndIsActiveTrueAndIsDeletedFalse(
            Long entityId);

    // ── Tìm theo loại constraint ──────────────────────────────────────────────

    List<DpEntityConstraint> findAllByEntity_IdAndConstraintTypeAndIsDeletedFalse(
            Long entityId, String constraintType);

    // ── Xóa toàn bộ constraint của entity ────────────────────────────────────

    @Modifying
    @Query("UPDATE DpEntityConstraint c SET c.isDeleted = true " +
            "WHERE c.entity.id = :entityId")
    int softDeleteByEntityId(@Param("entityId") Long entityId);
}