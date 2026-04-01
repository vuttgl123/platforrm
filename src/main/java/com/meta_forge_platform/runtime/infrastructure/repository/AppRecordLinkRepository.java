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

    // ── Tìm link cụ thể ───────────────────────────────────────────────────────

    Optional<AppRecordLink> findBySourceRecord_IdAndField_IdAndTargetRecord_IdAndLinkTypeAndIsDeletedFalse(
            Long sourceRecordId, Long fieldId, Long targetRecordId, String linkType);

    // ── Tìm tất cả link từ một record ────────────────────────────────────────

    List<AppRecordLink> findAllBySourceRecord_IdAndIsDeletedFalseOrderBySortOrderAsc(
            Long sourceRecordId);

    List<AppRecordLink> findAllBySourceRecord_IdAndField_IdAndIsDeletedFalse(
            Long sourceRecordId, Long fieldId);

    List<AppRecordLink> findAllBySourceRecord_IdAndLinkTypeAndIsDeletedFalse(
            Long sourceRecordId, String linkType);

    // ── Tìm tất cả link đến một record ───────────────────────────────────────

    List<AppRecordLink> findAllByTargetRecord_IdAndIsDeletedFalse(Long targetRecordId);

    List<AppRecordLink> findAllByTargetRecord_IdAndLinkTypeAndIsDeletedFalse(
            Long targetRecordId, String linkType);

    // ── Kiểm tra link tồn tại ────────────────────────────────────────────────

    boolean existsBySourceRecord_IdAndField_IdAndTargetRecord_IdAndIsDeletedFalse(
            Long sourceRecordId, Long fieldId, Long targetRecordId);

    // ── Xóa toàn bộ link của record (cả hai chiều) ───────────────────────────

    @Modifying
    @Query("UPDATE AppRecordLink l SET l.isDeleted = true " +
            "WHERE l.sourceRecord.id = :recordId OR l.targetRecord.id = :recordId")
    int softDeleteByRecordId(@Param("recordId") Long recordId);

    // ── Xóa link theo source + field ─────────────────────────────────────────

    @Modifying
    @Query("UPDATE AppRecordLink l SET l.isDeleted = true " +
            "WHERE l.sourceRecord.id = :sourceRecordId AND l.field.id = :fieldId")
    int softDeleteBySourceAndField(@Param("sourceRecordId") Long sourceRecordId,
                                   @Param("fieldId") Long fieldId);

    // ── Count link theo relation ──────────────────────────────────────────────

    long countBySourceRecord_IdAndField_IdAndIsDeletedFalse(Long sourceRecordId, Long fieldId);
}