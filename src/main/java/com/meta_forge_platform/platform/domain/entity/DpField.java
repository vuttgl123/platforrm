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
@Table(name = "dp_field",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_field_entity_code",
                columnNames = {"entity_id", "field_code"}))
@SQLDelete(sql = "UPDATE dp_field SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class DpField extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_field_entity"))
    private DpEntity entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_group_id", foreignKey = @ForeignKey(name = "fk_dp_field_group"))
    private DpFieldGroup fieldGroup;

    @Column(name = "field_code", nullable = false, length = 100)
    private String fieldCode;

    @Column(name = "field_name", nullable = false, length = 255)
    private String fieldName;

    @Column(name = "data_type", nullable = false, length = 50)
    private String dataType;

    @Column(name = "ui_type", length = 50)
    private String uiType;

    @Column(name = "storage_type", nullable = false, length = 30)
    @Builder.Default
    private String storageType = "SCALAR";

    @Column(name = "is_required", nullable = false)
    @Builder.Default
    private Boolean isRequired = false;

    @Column(name = "is_unique_field", nullable = false)
    @Builder.Default
    private Boolean isUniqueField = false;

    @Column(name = "is_searchable", nullable = false)
    @Builder.Default
    private Boolean isSearchable = false;

    @Column(name = "is_filterable", nullable = false)
    @Builder.Default
    private Boolean isFilterable = false;

    @Column(name = "is_sortable", nullable = false)
    @Builder.Default
    private Boolean isSortable = false;

    @Column(name = "is_listable", nullable = false)
    @Builder.Default
    private Boolean isListable = true;

    @Column(name = "is_detail_visible", nullable = false)
    @Builder.Default
    private Boolean isDetailVisible = true;

    @Column(name = "is_editable", nullable = false)
    @Builder.Default
    private Boolean isEditable = true;

    @Column(name = "is_system", nullable = false)
    @Builder.Default
    private Boolean isSystem = false;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "default_value_json", columnDefinition = "JSON")
    private Map<String, Object> defaultValueJson;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "validation_json", columnDefinition = "JSON")
    private Map<String, Object> validationJson;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> configJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relation_entity_id", foreignKey = @ForeignKey(name = "fk_dp_field_relation_entity"))
    private DpEntity relationEntity;

    @Column(name = "relation_type", length = 30)
    private String relationType;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DpFieldOption> options = new ArrayList<>();
}