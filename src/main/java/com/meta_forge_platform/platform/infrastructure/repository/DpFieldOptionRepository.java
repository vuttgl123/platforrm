package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpFieldOption;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpFieldOptionRepository extends BaseRepository<DpFieldOption, Long> {

    Optional<DpFieldOption> findByField_IdAndOptionCodeAndIsDeletedFalse(Long fieldId, String optionCode);

    boolean existsByField_IdAndOptionCodeAndIsDeletedFalse(Long fieldId, String optionCode);

    List<DpFieldOption> findAllByField_IdAndIsDeletedFalseOrderBySortOrderAsc(Long fieldId);

    List<DpFieldOption> findAllByField_IdAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(Long fieldId);

    Optional<DpFieldOption> findByField_IdAndIsDefaultTrueAndIsDeletedFalse(Long fieldId);

    @Modifying
    @Query("UPDATE DpFieldOption o SET o.isDeleted = true WHERE o.field.id = :fieldId")
    int softDeleteByFieldId(@Param("fieldId") Long fieldId);

    Optional<DpFieldOption> findByField_IdAndOptionValueAndIsDeletedFalse(Long fieldId, String optionValue);
}