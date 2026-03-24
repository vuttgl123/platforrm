package com.meta_forge_platform.runtime.infrastructure.persistence.record;

import com.meta_forge_platform.runtime.domain.model.record.AppRecordLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppRecordLinkRepository extends JpaRepository<AppRecordLink, Long> {

    List<AppRecordLink> findBySourceRecord_IdOrderBySortOrderAscIdAsc(Long sourceRecordId);

    List<AppRecordLink> findByTargetRecord_IdOrderBySortOrderAscIdAsc(Long targetRecordId);

    List<AppRecordLink> findBySourceRecord_IdAndRelation_IdOrderBySortOrderAscIdAsc(Long sourceRecordId, Long relationId);

    List<AppRecordLink> findByTargetRecord_IdAndRelation_IdOrderBySortOrderAscIdAsc(Long targetRecordId, Long relationId);

    boolean existsBySourceRecord_IdAndTargetRecord_IdAndRelation_Id(Long sourceRecordId, Long targetRecordId, Long relationId);

    void deleteBySourceRecord_Id(Long sourceRecordId);

    void deleteByTargetRecord_Id(Long targetRecordId);
}
