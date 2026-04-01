package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpMenu;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpMenuRepository extends BaseRepository<DpMenu, Long> {

    Optional<DpMenu> findByModule_IdAndMenuCodeAndIsDeletedFalse(
            Long moduleId, String menuCode);

    boolean existsByModule_IdAndMenuCodeAndIsDeletedFalse(
            Long moduleId, String menuCode);

    // ── Tìm menu gốc (không có parent) theo module ───────────────────────────

    List<DpMenu> findAllByModule_IdAndParentMenuIsNullAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(
            Long moduleId);

    // ── Tìm menu con ─────────────────────────────────────────────────────────

    List<DpMenu> findAllByParentMenu_IdAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(
            Long parentMenuId);

    // ── Tìm tất cả menu của module ────────────────────────────────────────────

    List<DpMenu> findAllByModule_IdAndIsDeletedFalseOrderBySortOrderAsc(Long moduleId);

    // ── Tìm theo screen ───────────────────────────────────────────────────────

    List<DpMenu> findAllByScreen_IdAndIsDeletedFalse(Long screenId);

    // ── Lấy toàn bộ cây menu của module (dùng để build navigation tree) ──────

    @Query("SELECT m FROM DpMenu m WHERE m.module.id = :moduleId " +
            "AND m.isDeleted = false AND m.isActive = true " +
            "ORDER BY m.sortOrder ASC")
    List<DpMenu> findMenuTreeByModule(@Param("moduleId") Long moduleId);

    // ── Tìm theo menu type ────────────────────────────────────────────────────

    List<DpMenu> findAllByModule_IdAndMenuTypeAndIsDeletedFalse(
            Long moduleId, String menuType);
}