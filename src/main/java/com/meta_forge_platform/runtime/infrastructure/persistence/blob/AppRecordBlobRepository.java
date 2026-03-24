package com.meta_forge_platform.runtime.infrastructure.persistence.blob;

import com.meta_forge_platform.runtime.domain.model.blob.AppRecordBlob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppRecordBlobRepository extends JpaRepository<AppRecordBlob, Long> {

    List<AppRecordBlob> findByRecord_IdOrderBySortOrderAscIdAsc(Long recordId);

    List<AppRecordBlob> findByRecord_IdAndField_IdOrderBySortOrderAscIdAsc(Long recordId, Long fieldId);

    List<AppRecordBlob> findByBlob_IdOrderByRecord_IdAscSortOrderAscIdAsc(Long blobId);

    Optional<AppRecordBlob> findByRecord_IdAndField_IdAndBlob_Id(Long recordId, Long fieldId, Long blobId);

    boolean existsByRecord_IdAndField_IdAndBlob_Id(Long recordId, Long fieldId, Long blobId);

    void deleteByRecord_Id(Long recordId);

    void deleteByRecord_IdAndField_Id(Long recordId, Long fieldId);
}
