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

    Optional<DpScreenField> findByScreen_IdAndField_IdAndSection_IdAndIsDeletedFalse(Long screenId, Long fieldId, Long sectionId);

    List<DpScreenField> findAllByScreen_IdAndIsDeletedFalseOrderBySortOrderAsc(Long screenId);

    List<DpScreenField> findAllBySection_IdAndIsDeletedFalseOrderBySortOrderAsc(Long sectionId);

    List<DpScreenField> findAllByScreen_IdAndSectionIsNullAndIsDeletedFalseOrderBySortOrderAsc(Long screenId);

    List<DpScreenField> findAllByScreen_IdAndIsHiddenFalseAndIsDeletedFalseOrderBySortOrderAsc(Long screenId);

    boolean existsByScreen_IdAndField_IdAndIsDeletedFalse(Long screenId, Long fieldId);

    List<DpScreenField> findAllByField_IdAndIsDeletedFalse(Long fieldId);

    @Modifying
    @Query("UPDATE DpScreenField sf SET sf.isDeleted = true " +
            "WHERE sf.screen.id = :screenId")
    int softDeleteByScreenId(@Param("screenId") Long screenId);

    @Modifying
    @Query("UPDATE DpScreenField sf SET sf.isDeleted = true " +
            "WHERE sf.section.id = :sectionId")
    int softDeleteBySectionId(@Param("sectionId") Long sectionId);
}