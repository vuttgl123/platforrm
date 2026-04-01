package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpView;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpViewRepository extends BaseRepository<DpView, Long> {

    Optional<DpView> findByEntity_IdAndViewCodeAndIsDeletedFalse(
            Long entityId, String viewCode);

    boolean existsByEntity_IdAndViewCodeAndIsDeletedFalse(
            Long entityId, String viewCode);

    // ── Tìm view mặc định ────────────────────────────────────────────────────

    Optional<DpView> findByEntity_IdAndIsDefaultTrueAndIsDeletedFalse(Long entityId);

    // ── Tìm tất cả view của entity ────────────────────────────────────────────

    List<DpView> findAllByEntity_IdAndIsDeletedFalse(Long entityId);

    List<DpView> findAllByEntity_IdAndIsActiveTrueAndIsDeletedFalse(Long entityId);

    // ── Tìm theo loại view ────────────────────────────────────────────────────

    List<DpView> findAllByEntity_IdAndViewTypeAndIsDeletedFalse(
            Long entityId, String viewType);

    // ── Reset default ─────────────────────────────────────────────────────────

    @Modifying
    @Query("UPDATE DpView v SET v.isDefault = false " +
            "WHERE v.entity.id = :entityId AND v.id <> :excludeId AND v.isDeleted = false")
    int unsetDefaultExcept(@Param("entityId") Long entityId,
                           @Param("excludeId") Long excludeId);

    // ── Xóa toàn bộ view của entity ──────────────────────────────────────────

    @Modifying
    @Query("UPDATE DpView v SET v.isDeleted = true WHERE v.entity.id = :entityId")
    int softDeleteByEntityId(@Param("entityId") Long entityId);
}