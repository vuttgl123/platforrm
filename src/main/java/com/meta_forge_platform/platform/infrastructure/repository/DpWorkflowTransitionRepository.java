package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpWorkflowTransition;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpWorkflowTransitionRepository extends BaseRepository<DpWorkflowTransition, Long> {

    Optional<DpWorkflowTransition> findByWorkflow_IdAndTransitionCodeAndIsDeletedFalse(
            Long workflowId, String transitionCode);

    // ── Tìm tất cả transition của workflow ────────────────────────────────────

    List<DpWorkflowTransition> findAllByWorkflow_IdAndIsDeletedFalseOrderBySortOrderAsc(
            Long workflowId);

    List<DpWorkflowTransition> findAllByWorkflow_IdAndIsActiveTrueAndIsDeletedFalse(
            Long workflowId);

    // ── Tìm transition có thể thực hiện từ một state ─────────────────────────

    List<DpWorkflowTransition> findAllByFromState_IdAndIsActiveTrueAndIsDeletedFalse(
            Long fromStateId);

    // ── Tìm transition dẫn đến một state ─────────────────────────────────────

    List<DpWorkflowTransition> findAllByToState_IdAndIsDeletedFalse(Long toStateId);

    // ── Tìm transition theo action code ──────────────────────────────────────

    Optional<DpWorkflowTransition> findByWorkflow_IdAndActionCodeAndIsDeletedFalse(
            Long workflowId, String actionCode);

    // ── Kiểm tra transition hợp lệ giữa hai state ────────────────────────────

    @Query("SELECT COUNT(t) > 0 FROM DpWorkflowTransition t " +
            "WHERE t.workflow.id = :workflowId AND t.isDeleted = false " +
            "AND t.isActive = true " +
            "AND t.fromState.id = :fromStateId AND t.toState.id = :toStateId")
    boolean isTransitionValid(@Param("workflowId") Long workflowId,
                              @Param("fromStateId") Long fromStateId,
                              @Param("toStateId") Long toStateId);

    // ── Xóa toàn bộ transition của workflow ──────────────────────────────────

    @Modifying
    @Query("UPDATE DpWorkflowTransition t SET t.isDeleted = true " +
            "WHERE t.workflow.id = :workflowId")
    int softDeleteByWorkflowId(@Param("workflowId") Long workflowId);
}