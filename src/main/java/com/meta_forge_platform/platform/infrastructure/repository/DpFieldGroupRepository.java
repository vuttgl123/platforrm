package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpFieldGroup;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpFieldGroupRepository extends BaseRepository<DpFieldGroup, Long> {

    Optional<DpFieldGroup> findByEntity_IdAndGroupCodeAndIsDeletedFalse(Long entityId, String groupCode);

    boolean existsByEntity_IdAndGroupCodeAndIsDeletedFalse(Long entityId, String groupCode);

    @Query("""
        SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END
        FROM DpFieldGroup g
        WHERE g.entity.id = :entityId
          AND g.groupCode = :code
          AND g.id <> :excludeId
          AND g.isDeleted = false
    """)
    boolean existsByEntityAndCodeExcludeId(@Param("entityId") Long entityId,
                                           @Param("code") String code,
                                           @Param("excludeId") Long excludeId);

    List<DpFieldGroup> findAllByEntity_IdAndIsDeletedFalseOrderBySortOrderAsc(Long entityId);

    List<DpFieldGroup> findAllByEntity_IdAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(Long entityId);

    @Modifying
    @Query("""
        UPDATE DpFieldGroup g
           SET g.isDeleted = true
         WHERE g.entity.id = :entityId
           AND g.isDeleted = false
    """)
    int softDeleteByEntityId(@Param("entityId") Long entityId);

    long countByEntity_IdAndIsDeletedFalse(Long entityId);
}