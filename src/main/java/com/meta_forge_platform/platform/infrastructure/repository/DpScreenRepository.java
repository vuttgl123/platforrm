package com.meta_forge_platform.platform.infrastructure.repository;

import com.meta_forge_platform.platform.domain.entity.DpScreen;
import com.meta_forge_platform.platform.domain.enumeration.ScreenType;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpScreenRepository extends BaseRepository<DpScreen, Long> {

    Optional<DpScreen> findByModule_IdAndScreenCodeAndIsDeletedFalse(Long moduleId, String screenCode);

    boolean existsByModule_IdAndScreenCodeAndIsDeletedFalse(Long moduleId, String screenCode);

    List<DpScreen> findAllByModule_IdAndIsDeletedFalseOrderBySortOrderAsc(Long moduleId);

    List<DpScreen> findAllByModule_IdAndIsActiveTrueAndIsDeletedFalseOrderBySortOrderAsc(Long moduleId);

    List<DpScreen> findAllByEntity_IdAndIsDeletedFalseOrderBySortOrderAsc(Long entityId);

    List<DpScreen> findAllByModule_IdAndScreenTypeAndIsDeletedFalseOrderBySortOrderAsc(Long moduleId, ScreenType screenType);

    Optional<DpScreen> findByRoutePathAndIsDeletedFalse(String routePath);

    @Query("""
        SELECT s
        FROM DpScreen s
        WHERE s.module.id = :moduleId
          AND s.isDeleted = false
          AND (
                LOWER(s.screenCode) LIKE LOWER(CONCAT('%', :kw, '%'))
             OR LOWER(s.screenName) LIKE LOWER(CONCAT('%', :kw, '%'))
          )
        ORDER BY s.sortOrder ASC
    """)
    List<DpScreen> searchByKeyword(@Param("moduleId") Long moduleId,
                                   @Param("kw") String keyword);
}