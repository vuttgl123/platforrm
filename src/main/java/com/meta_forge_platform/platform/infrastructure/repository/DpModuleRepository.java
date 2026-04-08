package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpModule;
import com.meta_forge_platform.platform.domain.enumeration.ModuleStatus;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpModuleRepository extends BaseRepository<DpModule, Long> {

    Optional<DpModule> findByModuleCodeAndIsDeletedFalse(String moduleCode);

    boolean existsByModuleCodeAndIsDeletedFalse(String moduleCode);

    @Query("""
        SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END
        FROM DpModule m
        WHERE m.moduleCode = :code
          AND m.id <> :excludeId
          AND m.isDeleted = false
    """)
    boolean existsByModuleCodeExcludeId(@Param("code") String code,
                                        @Param("excludeId") Long excludeId);

    List<DpModule> findAllByStatusAndIsDeletedFalseOrderBySortOrderAsc(ModuleStatus status);

    List<DpModule> findAllByIsDeletedFalseOrderBySortOrderAsc();

    List<DpModule> findAllByIsSystemTrueAndIsDeletedFalse();

    @Query("""
        SELECT m
        FROM DpModule m
        WHERE m.isDeleted = false
          AND (
                LOWER(m.moduleCode) LIKE LOWER(CONCAT('%', :kw, '%'))
             OR LOWER(m.moduleName) LIKE LOWER(CONCAT('%', :kw, '%'))
             OR LOWER(COALESCE(m.description, '')) LIKE LOWER(CONCAT('%', :kw, '%'))
          )
        ORDER BY m.sortOrder ASC
    """)
    List<DpModule> searchByKeyword(@Param("kw") String keyword);

    @Query("""
        SELECT m
        FROM DpModule m
        WHERE m.isDeleted = false
        ORDER BY m.sortOrder ASC
    """)
    List<DpModule> findAllActiveSorted();
}