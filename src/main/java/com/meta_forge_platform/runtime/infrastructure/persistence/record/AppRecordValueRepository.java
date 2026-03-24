package com.meta_forge_platform.runtime.infrastructure.persistence.record;

import com.meta_forge_platform.runtime.domain.model.record.AppRecordValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppRecordValueRepository extends JpaRepository<AppRecordValue, Long> {

    Optional<AppRecordValue> findByRecord_IdAndField_IdAndSeqNo(Long recordId, Long fieldId, Integer seqNo);

    boolean existsByRecord_IdAndField_IdAndSeqNo(Long recordId, Long fieldId, Integer seqNo);

    List<AppRecordValue> findByRecord_IdOrderByField_IdAscSeqNoAscIdAsc(Long recordId);

    List<AppRecordValue> findByRecord_IdAndField_IdOrderBySeqNoAscIdAsc(Long recordId, Long fieldId);

    List<AppRecordValue> findByEntity_IdAndField_IdOrderByRecord_IdAscSeqNoAscIdAsc(Long entityId, Long fieldId);

    void deleteByRecord_Id(Long recordId);

    void deleteByRecord_IdAndField_Id(Long recordId, Long fieldId);
}
