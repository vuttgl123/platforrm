package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpEntityRelation;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpEntityRelationRepository extends BaseRepository<DpEntityRelation, Long> {

    Optional<DpEntityRelation> findBySourceEntity_IdAndRelationCodeAndIsDeletedFalse(
            Long sourceEntityId, String relationCode);

    // ── Tìm theo source entity ────────────────────────────────────────────────

    List<DpEntityRelation> findAllBySourceEntity_IdAndIsDeletedFalse(Long sourceEntityId);

    List<DpEntityRelation> findAllBySourceEntity_IdAndRelationKindAndIsDeletedFalse(
            Long sourceEntityId, String relationKind);

    // ── Tìm theo target entity ────────────────────────────────────────────────

    List<DpEntityRelation> findAllByTargetEntity_IdAndIsDeletedFalse(Long targetEntityId);

    // ── Tìm tất cả quan hệ liên quan đến một entity (cả hai chiều) ───────────

    @Query("SELECT r FROM DpEntityRelation r WHERE r.isDeleted = false AND " +
            "(r.sourceEntity.id = :entityId OR r.targetEntity.id = :entityId)")
    List<DpEntityRelation> findAllRelatedToEntity(@Param("entityId") Long entityId);

    // ── Kiểm tra quan hệ đã tồn tại giữa hai entity chưa ────────────────────

    @Query("SELECT COUNT(r) > 0 FROM DpEntityRelation r WHERE r.isDeleted = false " +
            "AND r.sourceEntity.id = :sourceId AND r.targetEntity.id = :targetId " +
            "AND r.relationKind = :kind")
    boolean existsRelation(@Param("sourceId") Long sourceId,
                           @Param("targetId") Long targetId,
                           @Param("kind") String kind);
}