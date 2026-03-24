package com.meta_forge_platform.definition.domain.model.field;

import com.meta_forge_platform.definition.domain.model.entity.EntityDefinition;
import com.meta_forge_platform.shared.domain.base.BaseEntity;
import com.meta_forge_platform.shared.domain.enumtype.RelationType;
import com.meta_forge_platform.shared.domain.enumtype.StorageType;
import com.meta_forge_platform.shared.domain.exception.ValidationException;
import com.meta_forge_platform.shared.infrastructure.converter.JsonMapConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "dp_field",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dp_field_entity_code", columnNames = {"entity_id", "field_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class FieldDefinition extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "entity_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_dp_field_entity")
    )
    private EntityDefinition entity;

    @Column(name = "field_code", nullable = false, length = 100)
    private String code;

    @Column(name = "field_name", nullable = false, length = 255)
    private String name;

    @Column(name = "data_type", nullable = false, length = 50)
    private String dataType;

    @Column(name = "ui_type", length = 50)
    private String uiType;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type", nullable = false, length = 30)
    private StorageType storageType = StorageType.SCALAR;

    @Column(name = "is_required", nullable = false)
    private Boolean required = Boolean.FALSE;

    @Column(name = "is_unique_field", nullable = false)
    private Boolean uniqueField = Boolean.FALSE;

    @Column(name = "is_searchable", nullable = false)
    private Boolean searchable = Boolean.FALSE;

    @Column(name = "is_filterable", nullable = false)
    private Boolean filterable = Boolean.FALSE;

    @Column(name = "is_sortable", nullable = false)
    private Boolean sortable = Boolean.FALSE;

    @Column(name = "is_listable", nullable = false)
    private Boolean listable = Boolean.TRUE;

    @Column(name = "is_detail_visible", nullable = false)
    private Boolean detailVisible = Boolean.TRUE;

    @Column(name = "is_editable", nullable = false)
    private Boolean editable = Boolean.TRUE;

    @Column(name = "is_system", nullable = false)
    private Boolean system = Boolean.FALSE;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "default_value_json", columnDefinition = "json")
    private Map<String, Object> defaultValue = new LinkedHashMap<>();

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "validation_json", columnDefinition = "json")
    private Map<String, Object> validation = new LinkedHashMap<>();

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "relation_entity_id",
            foreignKey = @ForeignKey(name = "fk_dp_field_relation_entity")
    )
    private EntityDefinition relationEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "relation_type", length = 30)
    private RelationType relationType;

    public FieldDefinition(
            EntityDefinition entity,
            String code,
            String name,
            String dataType
    ) {
        changeEntity(entity);
        changeCode(code);
        changeName(name);
        changeDataType(dataType);
    }

    public void changeEntity(EntityDefinition entity) {
        if (entity == null) {
            throw new ValidationException("Entity must not be null");
        }
        this.entity = entity;
    }

    public void changeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Field code must not be blank");
        }
        this.code = code.trim();
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Field name must not be blank");
        }
        this.name = name.trim();
    }

    public void changeDataType(String dataType) {
        if (dataType == null || dataType.isBlank()) {
            throw new ValidationException("Field data type must not be blank");
        }
        this.dataType = dataType.trim();
    }

    public void changeUiType(String uiType) {
        this.uiType = uiType == null || uiType.isBlank() ? null : uiType.trim();
    }

    public void changeStorageType(StorageType storageType) {
        if (storageType == null) {
            throw new ValidationException("Storage type must not be null");
        }
        this.storageType = storageType;
    }

    public void changeDefaultValue(Map<String, Object> defaultValue) {
        this.defaultValue = defaultValue == null ? new LinkedHashMap<>() : new LinkedHashMap<>(defaultValue);
    }

    public void changeValidation(Map<String, Object> validation) {
        this.validation = validation == null ? new LinkedHashMap<>() : new LinkedHashMap<>(validation);
    }

    public void changeConfig(Map<String, Object> config) {
        this.config = config == null ? new LinkedHashMap<>() : new LinkedHashMap<>(config);
    }

    public void changeSortOrder(Integer sortOrder) {
        if (sortOrder == null || sortOrder < 0) {
            throw new ValidationException("Sort order must be greater than or equal to 0");
        }
        this.sortOrder = sortOrder;
    }

    public void markRequired() {
        this.required = Boolean.TRUE;
    }

    public void markOptional() {
        this.required = Boolean.FALSE;
    }

    public void markUnique() {
        this.uniqueField = Boolean.TRUE;
    }

    public void markNonUnique() {
        this.uniqueField = Boolean.FALSE;
    }

    public void markSearchable() {
        this.searchable = Boolean.TRUE;
    }

    public void markNonSearchable() {
        this.searchable = Boolean.FALSE;
    }

    public void markFilterable() {
        this.filterable = Boolean.TRUE;
    }

    public void markNonFilterable() {
        this.filterable = Boolean.FALSE;
    }

    public void markSortable() {
        this.sortable = Boolean.TRUE;
    }

    public void markNonSortable() {
        this.sortable = Boolean.FALSE;
    }

    public void markListable() {
        this.listable = Boolean.TRUE;
    }

    public void markNonListable() {
        this.listable = Boolean.FALSE;
    }

    public void markDetailVisible() {
        this.detailVisible = Boolean.TRUE;
    }

    public void markDetailHidden() {
        this.detailVisible = Boolean.FALSE;
    }

    public void markEditable() {
        this.editable = Boolean.TRUE;
    }

    public void markReadOnly() {
        this.editable = Boolean.FALSE;
    }

    public void markAsSystem() {
        this.system = Boolean.TRUE;
    }

    public void markAsCustom() {
        this.system = Boolean.FALSE;
    }

    public boolean isSystem() {
        return Boolean.TRUE.equals(system);
    }

    public void changeRelation(EntityDefinition relationEntity, RelationType relationType) {
        this.relationEntity = relationEntity;
        this.relationType = relationType;
    }

    public void clearRelation() {
        this.relationEntity = null;
        this.relationType = null;
    }

    public boolean isRelationalField() {
        return relationEntity != null && relationType != null;
    }
}
