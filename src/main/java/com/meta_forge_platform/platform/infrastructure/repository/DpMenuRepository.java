package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpMenu;
import com.meta_forge_platform.platform.domain.enumeration.MenuType;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpMenuRepository extends BaseRepository<DpMenu, Long> {

    Optional<DpMenu> findByModule_IdAndMenuCodeAndIsDeletedFalse(Long moduleId, String menuCode);

    boolean existsByModule_IdAndMenuCodeAndIsDeletedFalse(Long moduleId, String menuCode);

    List<DpMenu> findAllByModule_IdAndParentMenuIsNullAndIsDeletedFalseOrderBySortOrderAsc(Long moduleId);

    List<DpMenu> findAllByParentMenu_IdAndIsDeletedFalseOrderBySortOrderAsc(Long parentMenuId);

    List<DpMenu> findAllByModule_IdAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(Long moduleId);

    List<DpMenu> findAllByModule_IdAndIsDeletedFalseOrderBySortOrderAsc(Long moduleId);

    List<DpMenu> findAllByScreen_IdAndIsDeletedFalse(Long screenId);

    List<DpMenu> findAllByModule_IdAndMenuTypeAndIsDeletedFalseOrderBySortOrderAsc(Long moduleId, MenuType menuType);

    @Query("""
        SELECT m
        FROM DpMenu m
        WHERE m.module.id = :moduleId
          AND m.isDeleted = false
          AND m.isActive = true
        ORDER BY m.sortOrder ASC
    """)
    List<DpMenu> findMenuTreeByModule(@Param("moduleId") Long moduleId);
}