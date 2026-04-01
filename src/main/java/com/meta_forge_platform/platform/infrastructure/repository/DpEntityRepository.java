package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpEntityRepository extends BaseRepository<DpEntity, Long> {

    // ── Tìm theo module + code ────────────────────────────────────────────────

    Optional<DpEntity> findByModule_IdAndEntityCodeAndIsDeletedFalse(
            Long moduleId, String entityCode);

    boolean existsByModule_IdAndEntityCodeAndIsDeletedFalse(
            Long moduleId, String entityCode);

    @Query("SELECT COUNT(e) > 0 FROM DpEntity e WHERE e.module.id = :moduleId " +
            "AND e.entityCode = :code AND e.id <> :excludeId AND e.isDeleted = false")
    boolean existsByModuleAndCodeExcludeId(@Param("moduleId") Long moduleId,
                                           @Param("code") String code,
                                           @Param("excludeId") Long excludeId);

    // ── Tìm theo module ───────────────────────────────────────────────────────

    List<DpEntity> findAllByModule_IdAndIsDeletedFalseOrderByEntityNameAsc(Long moduleId);

    List<DpEntity> findAllByModule_IdAndIsActiveTrueAndIsDeletedFalse(Long moduleId);

    // ── Tìm root entity ───────────────────────────────────────────────────────

    List<DpEntity> findAllByIsRootTrueAndIsDeletedFalse();

    List<DpEntity> findAllByModule_IdAndIsRootTrueAndIsDeletedFalse(Long moduleId);

    // ── Tìm theo table strategy ───────────────────────────────────────────────

    List<DpEntity> findAllByTableStrategyAndIsDeletedFalse(String tableStrategy);

    // ── Search keyword trong module ───────────────────────────────────────────

    @Query("SELECT e FROM DpEntity e WHERE e.module.id = :moduleId " +
            "AND e.isDeleted = false AND (" +
            "LOWER(e.entityCode) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
            "LOWER(e.entityName) LIKE LOWER(CONCAT('%', :kw, '%')))")
    List<DpEntity> searchByKeywordInModule(@Param("moduleId") Long moduleId,
                                           @Param("kw") String keyword);

    // ── Count entity theo module ──────────────────────────────────────────────

    @Query("SELECT COUNT(e) FROM DpEntity e " +
            "WHERE e.module.id = :moduleId AND e.isDeleted = false")
    long countByModuleId(@Param("moduleId") Long moduleId);
}