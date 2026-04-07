package com.meta_forge_platform.runtime.infrastructure.repository;

import com.meta_forge_platform.runtime.domain.entity.AppRecordLink;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppRecordLinkRepository extends BaseRepository<AppRecordLink, Long> {

    Optional<AppRecordLink> findBySourceRecord_IdAndField_IdAndTargetRecord_IdAndLinkTypeAndIsDeletedFalse(Long sourceRecordId, Long fieldId, Long targetRecordId, String linkType);

    List<AppRecordLink> findAllBySourceRecord_IdAndIsDeletedFalseOrderBySortOrderAsc(Long sourceRecordId);

    List<AppRecordLink> findAllBySourceRecord_IdAndField_IdAndIsDeletedFalse(Long sourceRecordId, Long fieldId);

    List<AppRecordLink> findAllBySourceRecord_IdAndLinkTypeAndIsDeletedFalse(Long sourceRecordId, String linkType);

    List<AppRecordLink> findAllByTargetRecord_IdAndIsDeletedFalse(Long targetRecordId);

    List<AppRecordLink> findAllByTargetRecord_IdAndLinkTypeAndIsDeletedFalse(Long targetRecordId, String linkType);

    boolean existsBySourceRecord_IdAndField_IdAndTargetRecord_IdAndIsDeletedFalse(Long sourceRecordId, Long fieldId, Long targetRecordId);

    @Modifying
    @Query("UPDATE AppRecordLink l SET l.isDeleted = true " +
            "WHERE l.sourceRecord.id = :recordId OR l.targetRecord.id = :recordId")
    int softDeleteByRecordId(@Param("recordId") Long recordId);

    @Modifying
    @Query("UPDATE AppRecordLink l SET l.isDeleted = true " +
            "WHERE l.sourceRecord.id = :sourceRecordId AND l.field.id = :fieldId")
    int softDeleteBySourceAndField(@Param("sourceRecordId") Long sourceRecordId,
                                   @Param("fieldId") Long fieldId);

    long countBySourceRecord_IdAndField_IdAndIsDeletedFalse(Long sourceRecordId, Long fieldId);
}