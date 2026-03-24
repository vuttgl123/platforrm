package com.meta_forge_platform.runtime.infrastructure.persistence.workflow;

import com.meta_forge_platform.runtime.domain.model.workflow.AppRecordStateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppRecordStateHistoryRepository extends JpaRepository<AppRecordStateHistory, Long> {

    List<AppRecordStateHistory> findByRecord_IdOrderByCreatedAtDescIdDesc(Long recordId);

    List<AppRecordStateHistory> findByRecord_IdAndTransition_IdOrderByCreatedAtDescIdDesc(Long recordId, Long transitionId);
}
