package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpWorkflowState;
import com.meta_forge_platform.platform.domain.enumeration.WorkflowStateType;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpWorkflowStateRepository extends BaseRepository<DpWorkflowState, Long> {

    Optional<DpWorkflowState> findByWorkflow_IdAndStateCodeAndIsDeletedFalse(Long workflowId, String stateCode);

    boolean existsByWorkflow_IdAndStateCodeAndIsDeletedFalse(Long workflowId, String stateCode);

    Optional<DpWorkflowState> findByWorkflow_IdAndIsInitialTrueAndIsDeletedFalse(Long workflowId);

    List<DpWorkflowState> findAllByWorkflow_IdAndIsFinalTrueAndIsDeletedFalseOrderBySortOrderAsc(Long workflowId);

    List<DpWorkflowState> findAllByWorkflow_IdAndIsDeletedFalseOrderBySortOrderAsc(Long workflowId);

    List<DpWorkflowState> findAllByWorkflow_IdAndStateTypeAndIsDeletedFalseOrderBySortOrderAsc(Long workflowId, WorkflowStateType stateType);

    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
        FROM AppRecord r
        WHERE r.currentState.id = :stateId
          AND r.isDeleted = false
    """)
    boolean isStateInUse(@Param("stateId") Long stateId);

    @Query("""
        SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
        FROM DpWorkflowState s
        WHERE s.workflow.id = :workflowId
          AND s.isInitial = true
          AND s.id <> :excludeId
          AND s.isDeleted = false
    """)
    boolean existsOtherInitialState(@Param("workflowId") Long workflowId,
                                    @Param("excludeId") Long excludeId);

    @Modifying
    @Query("""
        UPDATE DpWorkflowState s
           SET s.isDeleted = true
         WHERE s.workflow.id = :workflowId
           AND s.isDeleted = false
    """)
    int softDeleteByWorkflowId(@Param("workflowId") Long workflowId);
}