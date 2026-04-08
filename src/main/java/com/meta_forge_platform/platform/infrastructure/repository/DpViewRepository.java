package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpView;
import com.meta_forge_platform.platform.domain.enumeration.ViewType;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpViewRepository extends BaseRepository<DpView, Long> {

    Optional<DpView> findByEntity_IdAndViewCodeAndIsDeletedFalse(Long entityId, String viewCode);

    boolean existsByEntity_IdAndViewCodeAndIsDeletedFalse(Long entityId, String viewCode);

    Optional<DpView> findByEntity_IdAndIsDefaultTrueAndIsDeletedFalse(Long entityId);

    List<DpView> findAllByEntity_IdAndIsDeletedFalseOrderBySortOrderAsc(Long entityId);

    List<DpView> findAllByEntity_IdAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(Long entityId);

    List<DpView> findAllByEntity_IdAndViewTypeAndIsDeletedFalseOrderBySortOrderAsc(Long entityId, ViewType viewType);

    @Modifying
    @Query("""
        UPDATE DpView v
           SET v.isDefault = false
         WHERE v.entity.id = :entityId
           AND v.id <> :excludeId
           AND v.isDeleted = false
    """)
    int unsetDefaultExcept(@Param("entityId") Long entityId,
                           @Param("excludeId") Long excludeId);

    @Modifying
    @Query("""
        UPDATE DpView v
           SET v.isDefault = false
         WHERE v.entity.id = :entityId
           AND v.isDeleted = false
    """)
    int unsetAllDefaultByEntityId(@Param("entityId") Long entityId);

    @Modifying
    @Query("""
        UPDATE DpView v
           SET v.isDeleted = true
         WHERE v.entity.id = :entityId
           AND v.isDeleted = false
    """)
    int softDeleteByEntityId(@Param("entityId") Long entityId);
}