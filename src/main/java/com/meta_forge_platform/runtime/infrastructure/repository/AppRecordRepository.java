package com.meta_forge_platform.runtime.infrastructure.repository;

import com.meta_forge_platform.runtime.domain.entity.AppRecord;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppRecordRepository extends BaseRepository<AppRecord, Long> {

    // ── Tìm theo entity + code ────────────────────────────────────────────────

    Optional<AppRecord> findByEntity_IdAndRecordCodeAndIsDeletedFalse(
            Long entityId, String recordCode);

    boolean existsByEntity_IdAndRecordCodeAndIsDeletedFalse(
            Long entityId, String recordCode);

    // ── Tìm theo entity ───────────────────────────────────────────────────────

    Page<AppRecord> findAllByEntity_IdAndIsDeletedFalse(Long entityId, Pageable pageable);

    List<AppRecord> findAllByEntity_IdAndStatusAndIsDeletedFalse(
            Long entityId, String status);

    // ── Tìm theo parent/root ──────────────────────────────────────────────────

    List<AppRecord> findAllByParentRecord_IdAndIsDeletedFalse(Long parentRecordId);

    List<AppRecord> findAllByRootRecord_IdAndIsDeletedFalse(Long rootRecordId);

    // ── Tìm theo trạng thái workflow ──────────────────────────────────────────

    Page<AppRecord> findAllByEntity_IdAndCurrentState_IdAndIsDeletedFalse(
            Long entityId, Long stateId, Pageable pageable);

    List<AppRecord> findAllByCurrentState_IdAndIsDeletedFalse(Long stateId);

    // ── Tìm record theo entity + status ──────────────────────────────────────

    @Query("SELECT r FROM AppRecord r WHERE r.entity.id = :entityId " +
            "AND r.isDeleted = false AND r.status = :status " +
            "ORDER BY r.createdAt DESC")
    Page<AppRecord> findByEntityAndStatus(@Param("entityId") Long entityId,
                                          @Param("status") String status,
                                          Pageable pageable);

    // ── Tìm kiếm theo display name ────────────────────────────────────────────

    @Query("SELECT r FROM AppRecord r WHERE r.entity.id = :entityId " +
            "AND r.isDeleted = false AND " +
            "LOWER(r.displayName) LIKE LOWER(CONCAT('%', :kw, '%'))")
    Page<AppRecord> searchByDisplayName(@Param("entityId") Long entityId,
                                        @Param("kw") String keyword,
                                        Pageable pageable);

    // ── Bulk update status ────────────────────────────────────────────────────

    @Modifying
    @Query("UPDATE AppRecord r SET r.status = :status " +
            "WHERE r.id IN :ids AND r.isDeleted = false")
    int bulkUpdateStatus(@Param("ids") List<Long> ids, @Param("status") String status);

    // ── Cập nhật trạng thái workflow ──────────────────────────────────────────

    @Modifying
    @Query("UPDATE AppRecord r SET r.currentState.id = :stateId " +
            "WHERE r.id = :recordId AND r.isDeleted = false")
    int updateCurrentState(@Param("recordId") Long recordId,
                           @Param("stateId") Long stateId);

    // ── Count theo entity ─────────────────────────────────────────────────────

    long countByEntity_IdAndIsDeletedFalse(Long entityId);

    @Query("SELECT COUNT(r) FROM AppRecord r WHERE r.entity.id = :entityId " +
            "AND r.status = :status AND r.isDeleted = false")
    long countByEntityAndStatus(@Param("entityId") Long entityId,
                                @Param("status") String status);
}