package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpScreenField;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpScreenFieldRepository extends BaseRepository<DpScreenField, Long> {

    Optional<DpScreenField> findByScreen_IdAndField_IdAndSection_IdAndIsDeletedFalse(
            Long screenId, Long fieldId, Long sectionId);

    // ── Tìm tất cả field của screen ───────────────────────────────────────────

    List<DpScreenField> findAllByScreen_IdAndIsDeletedFalseOrderBySortOrderAsc(
            Long screenId);

    // ── Tìm field theo section ────────────────────────────────────────────────

    List<DpScreenField> findAllBySection_IdAndIsDeletedFalseOrderBySortOrderAsc(
            Long sectionId);

    // ── Tìm field không có section (nằm thẳng trong screen) ──────────────────

    List<DpScreenField> findAllByScreen_IdAndSectionIsNullAndIsDeletedFalseOrderBySortOrderAsc(
            Long screenId);

    // ── Tìm field hiển thị (không hidden) ────────────────────────────────────

    List<DpScreenField> findAllByScreen_IdAndIsHiddenFalseAndIsDeletedFalseOrderBySortOrderAsc(
            Long screenId);

    // ── Kiểm tra field đã có trong screen chưa ───────────────────────────────

    boolean existsByScreen_IdAndField_IdAndIsDeletedFalse(Long screenId, Long fieldId);

    // ── Tìm tất cả screen chứa một field ─────────────────────────────────────

    List<DpScreenField> findAllByField_IdAndIsDeletedFalse(Long fieldId);

    // ── Xóa toàn bộ screen field của screen ──────────────────────────────────

    @Modifying
    @Query("UPDATE DpScreenField sf SET sf.isDeleted = true " +
            "WHERE sf.screen.id = :screenId")
    int softDeleteByScreenId(@Param("screenId") Long screenId);

    // ── Xóa toàn bộ screen field của section ─────────────────────────────────

    @Modifying
    @Query("UPDATE DpScreenField sf SET sf.isDeleted = true " +
            "WHERE sf.section.id = :sectionId")
    int softDeleteBySectionId(@Param("sectionId") Long sectionId);
}