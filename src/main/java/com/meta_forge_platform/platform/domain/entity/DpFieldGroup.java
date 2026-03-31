package com.meta_forge_platform.platform.domain.entity;

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
@Table(name = "dp_field_group",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_field_group_entity_code",
                columnNames = {"entity_id", "group_code"}))
@SQLDelete(sql = "UPDATE dp_field_group SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class DpFieldGroup extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_field_group_entity"))
    private DpEntity entity;

    @Column(name = "group_code", nullable = false, length = 100)
    private String groupCode;

    @Column(name = "group_name", nullable = false, length = 255)
    private String groupName;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> configJson;

    @OneToMany(mappedBy = "fieldGroup")
    @Builder.Default
    private List<DpField> fields = new ArrayList<>();
}