package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.platform.domain.enumeration.FieldDataType;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpFieldRepository extends BaseRepository<DpField, Long> {

    Optional<DpField> findByEntity_IdAndFieldCodeAndIsDeletedFalse(Long entityId, String fieldCode);

    boolean existsByEntity_IdAndFieldCodeAndIsDeletedFalse(Long entityId, String fieldCode);

    @Query("""
        SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END
        FROM DpField f
        WHERE f.entity.id = :entityId
          AND f.fieldCode = :code
          AND f.id <> :excludeId
          AND f.isDeleted = false
    """)
    boolean existsByEntityAndCodeExcludeId(@Param("entityId") Long entityId,
                                           @Param("code") String code,
                                           @Param("excludeId") Long excludeId);

    List<DpField> findAllByEntity_IdAndIsDeletedFalseOrderBySortOrderAsc(Long entityId);

    List<DpField> findAllByFieldGroup_IdAndIsDeletedFalseOrderBySortOrderAsc(Long groupId);

    List<DpField> findAllByEntity_IdAndIsSearchableTrueAndIsDeletedFalseOrderBySortOrderAsc(Long entityId);

    List<DpField> findAllByEntity_IdAndIsFilterableTrueAndIsDeletedFalseOrderBySortOrderAsc(Long entityId);

    List<DpField> findAllByEntity_IdAndIsListableTrueAndIsDeletedFalseOrderBySortOrderAsc(Long entityId);

    List<DpField> findAllByEntity_IdAndIsRequiredTrueAndIsDeletedFalseOrderBySortOrderAsc(Long entityId);

    List<DpField> findAllByEntity_IdAndIsSystemTrueAndIsDeletedFalseOrderBySortOrderAsc(Long entityId);

    List<DpField> findAllByRelationEntity_IdAndIsDeletedFalse(Long relationEntityId);

    List<DpField> findAllByEntity_IdAndDataTypeAndIsDeletedFalseOrderBySortOrderAsc(Long entityId, FieldDataType dataType);

    @Modifying
    @Query("""
        UPDATE DpField f
           SET f.isDeleted = true
         WHERE f.entity.id = :entityId
           AND f.isDeleted = false
    """)
    int softDeleteByEntityId(@Param("entityId") Long entityId);

    long countByEntity_IdAndIsDeletedFalse(Long entityId);

    @Query("""
        SELECT f
        FROM DpField f
        WHERE f.entity.id = :entityId
          AND f.isSearchable = true
          AND f.isDeleted = false
        ORDER BY f.sortOrder ASC
    """)
    List<DpField> findSearchableFields(@Param("entityId") Long entityId);
}