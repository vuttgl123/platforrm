package com.meta_forge_platform.runtime.domain.entity;

import com.meta_forge_platform.platform.domain.entity.DpEntity;
import com.meta_forge_platform.platform.domain.entity.DpEntityRelation;
import com.meta_forge_platform.platform.domain.entity.DpField;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_record_link",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_app_record_link_unique",
                columnNames = {"source_record_id", "field_id", "target_record_id", "link_type"}))
@SQLDelete(sql = "UPDATE app_record_link SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class AppRecordLink extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relation_id", foreignKey = @ForeignKey(name = "fk_app_record_link_relation"))
    private DpEntityRelation relation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_record_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_link_source_record"))
    private AppRecord sourceRecord;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_link_source_entity"))
    private DpEntity sourceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", foreignKey = @ForeignKey(name = "fk_app_record_link_field"))
    private DpField field;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_record_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_link_target_record"))
    private AppRecord targetRecord;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_link_target_entity"))
    private DpEntity targetEntity;

    @Column(name = "link_type", nullable = false, length = 30)
    @Builder.Default
    private String linkType = "REFERENCE";

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;
}