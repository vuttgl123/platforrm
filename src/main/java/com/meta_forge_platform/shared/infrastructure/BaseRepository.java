package com.meta_forge_platform.shared.infrastructure;

import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends SoftDeletableEntity, ID>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.isDeleted = false")
    Optional<T> findActiveById(@Param("id") ID id);

    @Query("SELECT e FROM #{#entityName} e WHERE e.isDeleted = false")
    List<T> findAllActive();

    @Modifying
    @Query("""
        UPDATE #{#entityName} e
           SET e.isDeleted = true,
               e.deletedAt = :deletedAt,
               e.deletedBy = :deletedBy
         WHERE e.id = :id
           AND e.isDeleted = false
    """)
    int softDeleteById(@Param("id") ID id,
                       @Param("deletedAt") LocalDateTime deletedAt,
                       @Param("deletedBy") String deletedBy);

    @Modifying
    @Query("""
        UPDATE #{#entityName} e
           SET e.isDeleted = false,
               e.deletedAt = null,
               e.deletedBy = null
         WHERE e.id = :id
           AND e.isDeleted = true
    """)
    int restoreById(@Param("id") ID id);

    @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.isDeleted = false")
    long countActive();

    @Query("""
        SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
          FROM #{#entityName} e
         WHERE e.id = :id
           AND e.isDeleted = false
    """)
    boolean existsActiveById(@Param("id") ID id);
}