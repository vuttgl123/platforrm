package com.meta_forge_platform.runtime.infrastructure.repository;

import com.meta_forge_platform.runtime.domain.entity.AppRecordValue;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppRecordValueRepository extends BaseRepository<AppRecordValue, Long> {

    // ── Tìm giá trị của một field trong record ────────────────────────────────

    Optional<AppRecordValue> findByRecord_IdAndField_IdAndSeqNoAndIsDeletedFalse(
            Long recordId, Long fieldId, Integer seqNo);

    List<AppRecordValue> findAllByRecord_IdAndField_IdAndIsDeletedFalse(
            Long recordId, Long fieldId);

    // ── Lấy tất cả giá trị của record ────────────────────────────────────────

    List<AppRecordValue> findAllByRecord_IdAndIsDeletedFalse(Long recordId);

    // ── Lấy giá trị theo entity + field (dùng để filter/search) ──────────────

    List<AppRecordValue> findAllByEntity_IdAndField_IdAndIsDeletedFalse(
            Long entityId, Long fieldId);

    // ── Tìm record theo giá trị string của field ─────────────────────────────

    @Query("SELECT v.record FROM AppRecordValue v WHERE v.field.id = :fieldId " +
            "AND v.valueString = :value AND v.isDeleted = false " +
            "AND v.record.isDeleted = false")
    List<AppRecordValue> findByFieldAndStringValue(@Param("fieldId") Long fieldId,
                                                   @Param("value") String value);

    // ── Tìm record theo giá trị integer (dùng cho REFERENCE field) ───────────

    @Query("SELECT v FROM AppRecordValue v WHERE v.field.id = :fieldId " +
            "AND v.valueInteger = :value AND v.isDeleted = false")
    List<AppRecordValue> findByFieldAndIntegerValue(@Param("fieldId") Long fieldId,
                                                    @Param("value") Long value);

    // ── Xóa toàn bộ value của record ─────────────────────────────────────────

    @Modifying
    @Query("UPDATE AppRecordValue v SET v.isDeleted = true " +
            "WHERE v.record.id = :recordId")
    int softDeleteByRecordId(@Param("recordId") Long recordId);

    // ── Xóa value của một field cụ thể trong record ───────────────────────────

    @Modifying
    @Query("UPDATE AppRecordValue v SET v.isDeleted = true " +
            "WHERE v.record.id = :recordId AND v.field.id = :fieldId")
    int softDeleteByRecordAndField(@Param("recordId") Long recordId,
                                   @Param("fieldId") Long fieldId);

    // ── Kiểm tra unique value của field ──────────────────────────────────────

    @Query("SELECT COUNT(v) > 0 FROM AppRecordValue v " +
            "WHERE v.field.id = :fieldId AND v.valueString = :value " +
            "AND v.record.id <> :excludeRecordId AND v.isDeleted = false")
    boolean existsDuplicateStringValue(@Param("fieldId") Long fieldId,
                                       @Param("value") String value,
                                       @Param("excludeRecordId") Long excludeRecordId);
}