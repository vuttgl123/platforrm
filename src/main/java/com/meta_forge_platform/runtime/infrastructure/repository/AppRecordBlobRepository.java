package com.meta_forge_platform.runtime.infrastructure.repository;

import com.meta_forge_platform.runtime.domain.entity.AppRecordBlob;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppRecordBlobRepository extends BaseRepository<AppRecordBlob, Long> {

    Optional<AppRecordBlob> findByRecord_IdAndField_IdAndBlob_IdAndIsDeletedFalse(Long recordId, Long fieldId, Long blobId);

    List<AppRecordBlob> findAllByRecord_IdAndIsDeletedFalseOrderBySortOrderAsc(Long recordId);

    List<AppRecordBlob> findAllByRecord_IdAndField_IdAndIsDeletedFalseOrderBySortOrderAsc(Long recordId, Long fieldId);

    Optional<AppRecordBlob> findFirstByRecord_IdAndField_IdAndIsDeletedFalseOrderBySortOrderAsc(Long recordId, Long fieldId);

    boolean existsByRecord_IdAndField_IdAndBlob_IdAndIsDeletedFalse(Long recordId, Long fieldId, Long blobId);

    @Modifying
    @Query("UPDATE AppRecordBlob rb SET rb.isDeleted = true " +
            "WHERE rb.record.id = :recordId")
    int softDeleteByRecordId(@Param("recordId") Long recordId);

    @Modifying
    @Query("UPDATE AppRecordBlob rb SET rb.isDeleted = true " +
            "WHERE rb.record.id = :recordId AND rb.field.id = :fieldId")
    int softDeleteByRecordAndField(@Param("recordId") Long recordId,
                                   @Param("fieldId") Long fieldId);

    long countByRecord_IdAndField_IdAndIsDeletedFalse(Long recordId, Long fieldId);

    List<AppRecordBlob> findAllByBlob_IdAndIsDeletedFalse(Long blobId);
}