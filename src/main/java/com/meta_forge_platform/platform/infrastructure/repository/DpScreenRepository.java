package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpScreen;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpScreenRepository extends BaseRepository<DpScreen, Long> {

    Optional<DpScreen> findByModule_IdAndScreenCodeAndIsDeletedFalse(
            Long moduleId, String screenCode);

    boolean existsByModule_IdAndScreenCodeAndIsDeletedFalse(
            Long moduleId, String screenCode);

    // ── Tìm theo module ───────────────────────────────────────────────────────

    List<DpScreen> findAllByModule_IdAndIsDeletedFalseOrderBySortOrderAsc(Long moduleId);

    List<DpScreen> findAllByModule_IdAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(
            Long moduleId);

    // ── Tìm theo entity ───────────────────────────────────────────────────────

    List<DpScreen> findAllByEntity_IdAndIsDeletedFalse(Long entityId);

    // ── Tìm theo screen type ──────────────────────────────────────────────────

    List<DpScreen> findAllByModule_IdAndScreenTypeAndIsDeletedFalse(
            Long moduleId, String screenType);

    // ── Tìm theo route path ───────────────────────────────────────────────────

    Optional<DpScreen> findByRoutePathAndIsDeletedFalse(String routePath);

    // ── Search ────────────────────────────────────────────────────────────────

    @Query("SELECT s FROM DpScreen s WHERE s.module.id = :moduleId " +
            "AND s.isDeleted = false AND (" +
            "LOWER(s.screenCode) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(s.screenName) LIKE LOWER(CONCAT('%', :kw, '%')))")
    List<DpScreen> searchByKeyword(@Param("moduleId") Long moduleId,
                                   @Param("kw") String keyword);
}