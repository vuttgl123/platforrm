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

    Optional<DpEntity> findByModule_IdAndEntityCodeAndIsDeletedFalse(Long moduleId, String entityCode);

    boolean existsByModule_IdAndEntityCodeAndIsDeletedFalse(Long moduleId, String entityCode);

    @Query("""
        SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
          FROM DpEntity e
         WHERE e.module.id = :moduleId
           AND e.entityCode = :entityCode
           AND e.id <> :excludeId
           AND e.isDeleted = false
    """)
    boolean existsByModuleAndCodeExcludeId(@Param("moduleId") Long moduleId,
                                           @Param("entityCode") String entityCode,
                                           @Param("excludeId") Long excludeId);

    List<DpEntity> findAllByModule_IdAndIsDeletedFalseOrderByEntityNameAsc(Long moduleId);

    List<DpEntity> findAllByModule_IdAndIsRootTrueAndIsDeletedFalseOrderByEntityNameAsc(Long moduleId);

    @Query("""
        SELECT e
          FROM DpEntity e
         WHERE e.isDeleted = false
         ORDER BY e.entityName ASC
    """)
    List<DpEntity> findAllActiveEntities();

    @Query("""
        SELECT e
          FROM DpEntity e
         WHERE e.module.id = :moduleId
           AND e.isDeleted = false
           AND (
                LOWER(e.entityCode) LIKE LOWER(CONCAT('%', :keyword, '%'))
             OR LOWER(e.entityName) LIKE LOWER(CONCAT('%', :keyword, '%'))
             OR LOWER(COALESCE(e.description, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
           )
         ORDER BY e.entityName ASC
    """)
    List<DpEntity> searchByKeywordInModule(@Param("moduleId") Long moduleId,
                                           @Param("keyword") String keyword);
}