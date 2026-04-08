package com.meta_forge_platform.runtime.infrastructure.repository;

import com.meta_forge_platform.runtime.domain.entity.AppRecordLink;
import com.meta_forge_platform.runtime.domain.enumeration.RecordLinkType;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppRecordLinkRepository extends BaseRepository<AppRecordLink, Long> {

    Optional<AppRecordLink> findBySourceRecord_IdAndField_IdAndTargetRecord_IdAndLinkTypeAndIsDeletedFalse(
            Long sourceRecordId, Long fieldId, Long targetRecordId, RecordLinkType linkType
    );

    List<AppRecordLink> findAllBySourceRecord_IdAndIsDeletedFalseOrderBySortOrderAsc(Long sourceRecordId);

    List<AppRecordLink> findAllBySourceRecord_IdAndField_IdAndIsDeletedFalseOrderBySortOrderAsc(Long sourceRecordId, Long fieldId);

    List<AppRecordLink> findAllBySourceRecord_IdAndLinkTypeAndIsDeletedFalseOrderBySortOrderAsc(Long sourceRecordId, RecordLinkType linkType);

    List<AppRecordLink> findAllByTargetRecord_IdAndIsDeletedFalseOrderBySortOrderAsc(Long targetRecordId);

    List<AppRecordLink> findAllByTargetRecord_IdAndLinkTypeAndIsDeletedFalseOrderBySortOrderAsc(Long targetRecordId, RecordLinkType linkType);

    boolean existsBySourceRecord_IdAndField_IdAndTargetRecord_IdAndIsDeletedFalse(Long sourceRecordId, Long fieldId, Long targetRecordId);

    @Modifying
    @Query("""
        UPDATE AppRecordLink l
           SET l.isDeleted = true
         WHERE (l.sourceRecord.id = :recordId OR l.targetRecord.id = :recordId)
           AND l.isDeleted = false
    """)
    int softDeleteByRecordId(@Param("recordId") Long recordId);

    @Modifying
    @Query("""
        UPDATE AppRecordLink l
           SET l.isDeleted = true
         WHERE l.sourceRecord.id = :sourceRecordId
           AND l.field.id = :fieldId
           AND l.isDeleted = false
    """)
    int softDeleteBySourceAndField(@Param("sourceRecordId") Long sourceRecordId,
                                   @Param("fieldId") Long fieldId);

    long countBySourceRecord_IdAndField_IdAndIsDeletedFalse(Long sourceRecordId, Long fieldId);

}