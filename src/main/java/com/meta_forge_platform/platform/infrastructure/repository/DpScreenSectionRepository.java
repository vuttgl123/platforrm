package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpScreenSection;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpScreenSectionRepository extends BaseRepository<DpScreenSection, Long> {

    Optional<DpScreenSection> findByScreen_IdAndSectionCodeAndIsDeletedFalse(Long screenId, String sectionCode);

    boolean existsByScreen_IdAndSectionCodeAndIsDeletedFalse(Long screenId, String sectionCode);

    List<DpScreenSection> findAllByScreen_IdAndIsDeletedFalseOrderBySortOrderAsc(Long screenId);

    List<DpScreenSection> findAllByScreen_IdAndParentSectionIsNullAndIsDeletedFalseOrderBySortOrderAsc(Long screenId);

    List<DpScreenSection> findAllByParentSection_IdAndIsDeletedFalseOrderBySortOrderAsc(Long parentSectionId);

    @Modifying
    @Query("""
        UPDATE DpScreenSection s
           SET s.isDeleted = true
         WHERE s.screen.id = :screenId
           AND s.isDeleted = false
    """)
    int softDeleteByScreenId(@Param("screenId") Long screenId);
}