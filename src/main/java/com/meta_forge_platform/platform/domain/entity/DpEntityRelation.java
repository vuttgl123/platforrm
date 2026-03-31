package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dp_entity_relation",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_entity_relation_code",
                columnNames = {"source_entity_id", "relation_code"}))
@SQLDelete(sql = "UPDATE dp_entity_relation SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class DpEntityRelation extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_entity_relation_source"))
    private DpEntity sourceEntity;

    @Column(name = "relation_code", nullable = false, length = 100)
    private String relationCode;

    @Column(name = "relation_name", nullable = false, length = 255)
    private String relationName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_entity_relation_target"))
    private DpEntity targetEntity;

    @Column(name = "relation_kind", nullable = false, length = 30)
    private String relationKind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mapped_by_field_id", foreignKey = @ForeignKey(name = "fk_dp_entity_relation_mapped_by_field"))
    private DpField mappedByField;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> configJson;
}