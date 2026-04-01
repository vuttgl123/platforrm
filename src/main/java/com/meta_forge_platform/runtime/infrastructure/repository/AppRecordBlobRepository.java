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

    // ── Tìm blob cụ thể ───────────────────────────────────────────────────────

    Optional<AppRecordBlob> findByRecord_IdAndField_IdAndBlob_IdAndIsDeletedFalse(
            Long recordId, Long fieldId, Long blobId);

    // ── Lấy tất cả file của record ────────────────────────────────────────────

    List<AppRecordBlob> findAllByRecord_IdAndIsDeletedFalseOrderBySortOrderAsc(
            Long recordId);

    // ── Lấy file theo field ───────────────────────────────────────────────────

    List<AppRecordBlob> findAllByRecord_IdAndField_IdAndIsDeletedFalseOrderBySortOrderAsc(
            Long recordId, Long fieldId);

    // ── Lấy ảnh đầu tiên (thumbnail) ─────────────────────────────────────────

    Optional<AppRecordBlob> findFirstByRecord_IdAndField_IdAndIsDeletedFalseOrderBySortOrderAsc(
            Long recordId, Long fieldId);

    // ── Kiểm tra file đã gắn với record + field chưa ─────────────────────────

    boolean existsByRecord_IdAndField_IdAndBlob_IdAndIsDeletedFalse(
            Long recordId, Long fieldId, Long blobId);

    // ── Xóa toàn bộ blob của record ───────────────────────────────────────────

    @Modifying
    @Query("UPDATE AppRecordBlob rb SET rb.isDeleted = true " +
            "WHERE rb.record.id = :recordId")
    int softDeleteByRecordId(@Param("recordId") Long recordId);

    // ── Xóa blob theo field ───────────────────────────────────────────────────

    @Modifying
    @Query("UPDATE AppRecordBlob rb SET rb.isDeleted = true " +
            "WHERE rb.record.id = :recordId AND rb.field.id = :fieldId")
    int softDeleteByRecordAndField(@Param("recordId") Long recordId,
                                   @Param("fieldId") Long fieldId);

    // ── Count file theo record + field ────────────────────────────────────────

    long countByRecord_IdAndField_IdAndIsDeletedFalse(Long recordId, Long fieldId);

    // ── Tìm tất cả record có gắn một blob cụ thể ─────────────────────────────

    List<AppRecordBlob> findAllByBlob_IdAndIsDeletedFalse(Long blobId);
}