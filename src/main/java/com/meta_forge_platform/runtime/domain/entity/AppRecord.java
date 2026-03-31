package com.meta_forge_platform.runtime.domain.entity;

import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowState;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_record",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_app_record_entity_code",
                columnNames = {"entity_id", "record_code"}))
@SQLDelete(sql = "UPDATE app_record SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class AppRecord extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_entity"))
    private DpEntity entity;

    @Column(name = "record_code", length = 100)
    private String recordCode;

    @Column(name = "display_name", length = 500)
    private String displayName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_state_id", foreignKey = @ForeignKey(name = "fk_app_record_state"))
    private DpWorkflowState currentState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_record_id", foreignKey = @ForeignKey(name = "fk_app_record_parent"))
    private AppRecord parentRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_record_id", foreignKey = @ForeignKey(name = "fk_app_record_root"))
    private AppRecord rootRecord;

    @Column(name = "status", nullable = false, length = 30)
    @Builder.Default
    private String status = "ACTIVE";

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "data_json", columnDefinition = "JSON")
    private Map<String, Object> dataJson;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AppRecordValue> values = new ArrayList<>();

    @OneToMany(mappedBy = "sourceRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AppRecordLink> outgoingLinks = new ArrayList<>();

    @OneToMany(mappedBy = "targetRecord")
    @Builder.Default
    private List<AppRecordLink> incomingLinks = new ArrayList<>();

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AppRecordBlob> blobs = new ArrayList<>();

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AppRecordStateHistory> stateHistories = new ArrayList<>();

    @OneToMany(mappedBy = "parentRecord")
    @Builder.Default
    private List<AppRecord> children = new ArrayList<>();
}