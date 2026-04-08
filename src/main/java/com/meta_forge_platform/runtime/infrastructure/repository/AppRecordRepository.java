package com.meta_forge_platform.runtime.infrastructure.repository;

import com.meta_forge_platform.runtime.domain.entity.AppRecord;
import com.meta_forge_platform.runtime.domain.enumeration.RecordStatus;
import com.meta_forge_platform.shared.infrastructure.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppRecordRepository extends BaseRepository<AppRecord, Long> {

    Optional<AppRecord> findByEntity_IdAndRecordCodeAndIsDeletedFalse(Long entityId, String recordCode);

    boolean existsByEntity_IdAndRecordCodeAndIsDeletedFalse(Long entityId, String recordCode);

    List<AppRecord> findAllByParentRecord_IdAndIsDeletedFalse(Long parentRecordId);

    List<AppRecord> findAllByRootRecord_IdAndIsDeletedFalse(Long rootRecordId);

    List<AppRecord> findAllByEntity_IdAndIsDeletedFalse(Long entityId);

    List<AppRecord> findAllByEntity_IdAndStatusAndIsDeletedFalse(Long entityId, RecordStatus status);
}