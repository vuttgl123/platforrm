package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpWorkflow;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpWorkflowRepository extends BaseRepository<DpWorkflow, Long> {

    Optional<DpWorkflow> findByEntity_IdAndWorkflowCodeAndIsDeletedFalse(
            Long entityId, String workflowCode);

    // ── Tìm workflow mặc định của entity ─────────────────────────────────────

    Optional<DpWorkflow> findByEntity_IdAndIsDefaultTrueAndIsDeletedFalse(Long entityId);

    // ── Tìm tất cả workflow của entity ───────────────────────────────────────

    List<DpWorkflow> findAllByEntity_IdAndIsDeletedFalse(Long entityId);

    List<DpWorkflow> findAllByEntity_IdAndIsActiveTrueAndIsDeletedFalse(Long entityId);

    // ── Reset default: bỏ default của tất cả workflow còn lại ────────────────

    @Modifying
    @Query("UPDATE DpWorkflow w SET w.isDefault = false " +
            "WHERE w.entity.id = :entityId AND w.id <> :excludeId AND w.isDeleted = false")
    int unsetDefaultExcept(@Param("entityId") Long entityId,
                           @Param("excludeId") Long excludeId);

    // ── Xóa toàn bộ workflow của entity ──────────────────────────────────────

    @Modifying
    @Query("UPDATE DpWorkflow w SET w.isDeleted = true WHERE w.entity.id = :entityId")
    int softDeleteByEntityId(@Param("entityId") Long entityId);
}