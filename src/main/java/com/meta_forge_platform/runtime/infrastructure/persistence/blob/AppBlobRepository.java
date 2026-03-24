package com.meta_forge_platform.runtime.infrastructure.persistence.blob;

import com.meta_forge_platform.runtime.domain.model.blob.AppBlob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppBlobRepository extends JpaRepository<AppBlob, Long> {

    Optional<AppBlob> findByCode(String code);

    boolean existsByCode(String code);

    Optional<AppBlob> findByStorageKey(String storageKey);

    boolean existsByStorageKey(String storageKey);
}
