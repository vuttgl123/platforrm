package com.meta_forge_platform.runtime.infrastructure.persistence.record;

import com.meta_forge_platform.runtime.domain.model.record.AppRecord;
import com.meta_forge_platform.shared.domain.constant.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppRecordRepository extends JpaRepository<AppRecord, Long> {

    Optional<AppRecord> findByEntity_IdAndRecordCode(Long entityId, String recordCode);

    boolean existsByEntity_IdAndRecordCode(Long entityId, String recordCode);

    List<AppRecord> findByEntity_IdOrderByIdDesc(Long entityId);

    List<AppRecord> findByEntity_IdAndStatusOrderByIdDesc(Long entityId, CommonStatus status);

    List<AppRecord> findByEntity_CodeOrderByIdDesc(String entityCode);
}
