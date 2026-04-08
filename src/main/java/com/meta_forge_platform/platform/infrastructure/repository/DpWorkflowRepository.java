package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpWorkflow;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpWorkflowRepository extends BaseRepository<DpWorkflow, Long> {

    Optional<DpWorkflow> findByEntity_IdAndWorkflowCodeAndIsDeletedFalse(Long entityId, String workflowCode);

    boolean existsByEntity_IdAndWorkflowCodeAndIsDeletedFalse(Long entityId, String workflowCode);

    Optional<DpWorkflow> findByEntity_IdAndIsDefaultTrueAndIsDeletedFalse(Long entityId);

    List<DpWorkflow> findAllByEntity_IdAndIsDeletedFalseOrderByWorkflowNameAsc(Long entityId);

    List<DpWorkflow> findAllByEntity_IdAndIsActiveTrueAndIsDeletedFalseOrderByWorkflowNameAsc(Long entityId);

    @Modifying
    @Query("""
        UPDATE DpWorkflow w
           SET w.isDefault = false
         WHERE w.entity.id = :entityId
           AND w.id <> :excludeId
           AND w.isDeleted = false
    """)
    int unsetDefaultExcept(@Param("entityId") Long entityId,
                           @Param("excludeId") Long excludeId);

    @Modifying
    @Query("""
        UPDATE DpWorkflow w
           SET w.isDefault = false
         WHERE w.entity.id = :entityId
           AND w.isDeleted = false
    """)
    int unsetAllDefaultByEntityId(@Param("entityId") Long entityId);

    @Modifying
    @Query("""
        UPDATE DpWorkflow w
           SET w.isDeleted = true
         WHERE w.entity.id = :entityId
           AND w.isDeleted = false
    """)
    int softDeleteByEntityId(@Param("entityId") Long entityId);
}