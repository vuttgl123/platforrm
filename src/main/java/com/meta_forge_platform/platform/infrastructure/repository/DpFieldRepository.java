package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpFieldRepository extends BaseRepository<DpField, Long> {

    Optional<DpField> findByEntity_IdAndFieldCodeAndIsDeletedFalse(
            Long entityId, String fieldCode);

    boolean existsByEntity_IdAndFieldCodeAndIsDeletedFalse(
            Long entityId, String fieldCode);

    @Query("SELECT COUNT(f) > 0 FROM DpField f WHERE f.entity.id = :entityId " +
            "AND f.fieldCode = :code AND f.id <> :excludeId AND f.isDeleted = false")
    boolean existsByEntityAndCodeExcludeId(@Param("entityId") Long entityId,
                                           @Param("code") String code,
                                           @Param("excludeId") Long excludeId);

    // ── Tìm theo entity ───────────────────────────────────────────────────────

    List<DpField> findAllByEntity_IdAndIsDeletedFalseOrderBySortOrderAsc(Long entityId);

    List<DpField> findAllByEntity_IdAndFieldGroup_IdAndIsDeletedFalse(
            Long entityId, Long groupId);

    // ── Tìm theo thuộc tính ───────────────────────────────────────────────────

    List<DpField> findAllByEntity_IdAndIsSearchableTrueAndIsDeletedFalse(Long entityId);

    List<DpField> findAllByEntity_IdAndIsFilterableTrueAndIsDeletedFalse(Long entityId);

    List<DpField> findAllByEntity_IdAndIsListableTrueAndIsDeletedFalseOrderBySortOrderAsc(
            Long entityId);

    List<DpField> findAllByEntity_IdAndIsRequiredTrueAndIsDeletedFalse(Long entityId);

    List<DpField> findAllByEntity_IdAndIsSystemTrueAndIsDeletedFalse(Long entityId);

    // ── Tìm field tham chiếu đến entity khác ─────────────────────────────────

    List<DpField> findAllByRelationEntity_IdAndIsDeletedFalse(Long relationEntityId);

    // ── Tìm theo data type ────────────────────────────────────────────────────

    List<DpField> findAllByEntity_IdAndDataTypeAndIsDeletedFalse(
            Long entityId, String dataType);

    // ── Xóa toàn bộ field của entity ─────────────────────────────────────────

    @Modifying
    @Query("UPDATE DpField f SET f.isDeleted = true WHERE f.entity.id = :entityId")
    int softDeleteByEntityId(@Param("entityId") Long entityId);

    // ── Count ─────────────────────────────────────────────────────────────────

    long countByEntity_IdAndIsDeletedFalse(Long entityId);
}