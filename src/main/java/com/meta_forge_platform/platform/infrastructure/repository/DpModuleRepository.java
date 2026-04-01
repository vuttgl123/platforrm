package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpModule;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpModuleRepository extends BaseRepository<DpModule, Long> {

    // ── Tìm theo code ─────────────────────────────────────────────────────────

    Optional<DpModule> findByModuleCodeAndIsDeletedFalse(String moduleCode);

    boolean existsByModuleCodeAndIsDeletedFalse(String moduleCode);

    // ── Kiểm tra trùng khi update ─────────────────────────────────────────────

    @Query("SELECT COUNT(m) > 0 FROM DpModule m " +
            "WHERE m.moduleCode = :code AND m.id <> :excludeId AND m.isDeleted = false")
    boolean existsByModuleCodeExcludeId(@Param("code") String code,
                                        @Param("excludeId") Long excludeId);

    // ── Tìm theo status ───────────────────────────────────────────────────────

    List<DpModule> findAllByStatusAndIsDeletedFalseOrderBySortOrderAsc(String status);

    // ── Tìm tất cả active, sắp xếp ───────────────────────────────────────────

    List<DpModule> findAllByIsDeletedFalseOrderBySortOrderAsc();

    // ── Tìm module system ─────────────────────────────────────────────────────

    List<DpModule> findAllByIsSystemTrueAndIsDeletedFalse();

    // ── Search keyword ────────────────────────────────────────────────────────

    @Query("SELECT m FROM DpModule m WHERE m.isDeleted = false AND " +
            "(LOWER(m.moduleCode) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            " LOWER(m.moduleName) LIKE LOWER(CONCAT('%', :kw, '%')))")
    List<DpModule> searchByKeyword(@Param("kw") String keyword);
}