package com.meta_forge_platform.runtime.domain.entity;

import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowState;
import com.meta_forge_platform.runtime.domain.enumeration.RecordStatus;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "app_record",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_app_record_entity_code",
                columnNames = {"entity_id", "record_code"}
        )
)
public class AppRecord extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false)
    private DpEntity entity;

    @Column(name = "record_code", length = 100)
    private String recordCode;

    @Column(name = "display_name", length = 500)
    private String displayName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_state_id")
    private DpWorkflowState currentState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_record_id")
    private AppRecord parentRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_record_id")
    private AppRecord rootRecord;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private RecordStatus status;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "data_json", columnDefinition = "JSON")
    private Map<String, Object> data;

    public static AppRecord create(
            DpEntity entity,
            String recordCode,
            Map<String, Object> data
    ) {
        AppRecord record = new AppRecord();
        record.entity = entity;
        record.recordCode = recordCode;
        record.data = data;
        record.status = RecordStatus.ACTIVE;
        return record;
    }

    public void applyMetadata(
            String displayName,
            RecordStatus status,
            Map<String, Object> data
    ) {
        this.displayName = displayName;
        this.status = status;
        this.data = data;
    }

    public void assignWorkflowState(DpWorkflowState state) {
        this.currentState = state;
    }

    public void assignParent(AppRecord parentRecord, AppRecord rootRecord) {
        this.parentRecord = parentRecord;
        this.rootRecord = rootRecord;
    }

    public void transitionTo(DpWorkflowState state) {
        this.currentState = state;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "AppRecord", getId());
        }
        softDelete(deletedBy);
    }
}