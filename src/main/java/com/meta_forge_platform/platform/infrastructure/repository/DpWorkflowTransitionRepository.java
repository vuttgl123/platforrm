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

    Optional<DpWorkflowTransition> findByWorkflow_IdAndTransitionCodeAndIsDeletedFalse(Long workflowId, String transitionCode);

    boolean existsByWorkflow_IdAndTransitionCodeAndIsDeletedFalse(Long workflowId, String transitionCode);

    List<DpWorkflowTransition> findAllByWorkflow_IdAndIsDeletedFalseOrderBySortOrderAsc(Long workflowId);

    List<DpWorkflowTransition> findAllByWorkflow_IdAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(Long workflowId);

    List<DpWorkflowTransition> findAllByFromState_IdAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(Long fromStateId);

    List<DpWorkflowTransition> findAllByToState_IdAndIsDeletedFalseOrderBySortOrderAsc(Long toStateId);

    Optional<DpWorkflowTransition> findByWorkflow_IdAndActionCodeAndIsDeletedFalse(Long workflowId, String actionCode);

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM DpWorkflowTransition t
        WHERE t.workflow.id = :workflowId
          AND t.isDeleted = false
          AND t.isActive = true
          AND t.fromState.id = :fromStateId
          AND t.toState.id = :toStateId
    """)
    boolean isTransitionValid(@Param("workflowId") Long workflowId,
                              @Param("fromStateId") Long fromStateId,
                              @Param("toStateId") Long toStateId);

    @Query("""
        SELECT t
        FROM DpWorkflowTransition t
        WHERE t.fromState.id = :fromStateId
          AND t.isActive = true
          AND t.isDeleted = false
        ORDER BY t.sortOrder ASC
    """)
    List<DpWorkflowTransition> findAvailableTransitions(@Param("fromStateId") Long fromStateId);

    @Modifying
    @Query("""
        UPDATE DpWorkflowTransition t
           SET t.isDeleted = true
         WHERE t.workflow.id = :workflowId
           AND t.isDeleted = false
    """)
    int softDeleteByWorkflowId(@Param("workflowId") Long workflowId);
}