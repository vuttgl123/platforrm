package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpWorkflowState;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpWorkflowStateRepository extends BaseRepository<DpWorkflowState, Long> {

    Optional<DpWorkflowState> findByWorkflow_IdAndStateCodeAndIsDeletedFalse(
            Long workflowId, String stateCode);

    // ── Tìm trạng thái khởi đầu ───────────────────────────────────────────────

    Optional<DpWorkflowState> findByWorkflow_IdAndIsInitialTrueAndIsDeletedFalse(
            Long workflowId);

    // ── Tìm tất cả trạng thái cuối ───────────────────────────────────────────

    List<DpWorkflowState> findAllByWorkflow_IdAndIsFinalTrueAndIsDeletedFalse(
            Long workflowId);

    // ── Tìm tất cả state của workflow ─────────────────────────────────────────

    List<DpWorkflowState> findAllByWorkflow_IdAndIsDeletedFalseOrderBySortOrderAsc(
            Long workflowId);

    // ── Tìm theo loại state ───────────────────────────────────────────────────

    List<DpWorkflowState> findAllByWorkflow_IdAndStateTypeAndIsDeletedFalse(
            Long workflowId, String stateType);

    // ── Kiểm tra state có đang được dùng bởi record không ────────────────────

    @Query("SELECT COUNT(r) > 0 FROM AppRecord r " +
            "WHERE r.currentState.id = :stateId AND r.isDeleted = false")
    boolean isStateInUse(@Param("stateId") Long stateId);

    // ── Xóa toàn bộ state của workflow ───────────────────────────────────────

    @Modifying
    @Query("UPDATE DpWorkflowState s SET s.isDeleted = true " +
            "WHERE s.workflow.id = :workflowId")
    int softDeleteByWorkflowId(@Param("workflowId") Long workflowId);
}