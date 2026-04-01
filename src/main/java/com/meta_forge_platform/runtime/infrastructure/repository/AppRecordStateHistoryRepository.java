package com.meta_forge_platform.runtime.infrastructure.repository;

import com.meta_forge_platform.runtime.domain.entity.AppRecordStateHistory;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppRecordStateHistoryRepository extends BaseRepository<AppRecordStateHistory, Long> {

    // ── Lấy toàn bộ lịch sử của record ───────────────────────────────────────

    List<AppRecordStateHistory> findAllByRecord_IdAndIsDeletedFalseOrderByChangedAtDesc(
            Long recordId);

    Page<AppRecordStateHistory> findAllByRecord_IdAndIsDeletedFalse(
            Long recordId, Pageable pageable);

    // ── Lấy trạng thái hiện tại (mới nhất) ───────────────────────────────────

    Optional<AppRecordStateHistory> findFirstByRecord_IdAndIsDeletedFalseOrderByChangedAtDesc(
            Long recordId);

    // ── Lấy lịch sử theo workflow ─────────────────────────────────────────────

    List<AppRecordStateHistory> findAllByRecord_IdAndWorkflow_IdAndIsDeletedFalse(
            Long recordId, Long workflowId);

    // ── Lấy lịch sử theo action ───────────────────────────────────────────────

    List<AppRecordStateHistory> findAllByRecord_IdAndActionCodeAndIsDeletedFalse(
            Long recordId, String actionCode);

    // ── Thống kê: số lần chuyển state trong khoảng thời gian ─────────────────

    @Query("SELECT COUNT(h) FROM AppRecordStateHistory h " +
            "WHERE h.record.entity.id = :entityId AND h.isDeleted = false " +
            "AND h.toState.id = :stateId " +
            "AND h.changedAt BETWEEN :from AND :to")
    long countTransitionsToState(@Param("entityId") Long entityId,
                                 @Param("stateId") Long stateId,
                                 @Param("from") LocalDateTime from,
                                 @Param("to") LocalDateTime to);

    // ── Kiểm tra xem record đã từng qua state nào chưa ───────────────────────

    @Query("SELECT COUNT(h) > 0 FROM AppRecordStateHistory h " +
            "WHERE h.record.id = :recordId AND h.toState.id = :stateId " +
            "AND h.isDeleted = false")
    boolean hasRecordBeenInState(@Param("recordId") Long recordId,
                                 @Param("stateId") Long stateId);
}