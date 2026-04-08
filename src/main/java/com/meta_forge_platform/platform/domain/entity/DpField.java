package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.platform.domain.enumeration.FieldDataType;
import com.meta_forge_platform.platform.domain.enumeration.FieldStorageType;
import com.meta_forge_platform.platform.domain.enumeration.FieldUiType;
import com.meta_forge_platform.platform.domain.enumeration.RelationType;
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
@Table(name = "dp_field",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_field_entity_code",
                columnNames = {"entity_id", "field_code"}))
public class DpField extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false)
    private DpEntity entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_group_id")
    private DpFieldGroup fieldGroup;

    @Column(name = "field_code", nullable = false, length = 100)
    private String fieldCode;

    @Column(name = "field_name", nullable = false, length = 255)
    private String fieldName;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false, length = 50)
    private FieldDataType dataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ui_type", length = 50)
    private FieldUiType uiType;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type", nullable = false, length = 30)
    private FieldStorageType storageType;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;

    @Column(name = "is_unique_field", nullable = false)
    private Boolean isUniqueField;

    @Column(name = "is_searchable", nullable = false)
    private Boolean isSearchable;

    @Column(name = "is_filterable", nullable = false)
    private Boolean isFilterable;

    @Column(name = "is_sortable", nullable = false)
    private Boolean isSortable;

    @Column(name = "is_listable", nullable = false)
    private Boolean isListable;

    @Column(name = "is_detail_visible", nullable = false)
    private Boolean isDetailVisible;

    @Column(name = "is_editable", nullable = false)
    private Boolean isEditable;

    @Column(name = "is_system", nullable = false)
    private Boolean isSystem;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "default_value_json", columnDefinition = "JSON")
    private Map<String, Object> defaultValue;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "validation_json", columnDefinition = "JSON")
    private Map<String, Object> validation;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> config;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relation_entity_id")
    private DpEntity relationEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "relation_type", length = 30)
    private RelationType relationType;

    public static DpField create(
            DpEntity entity,
            String code,
            String name,
            FieldDataType dataType
    ) {
        DpField f = new DpField();
        f.entity = entity;
        f.fieldCode = code;
        f.fieldName = name;
        f.dataType = dataType;
        f.storageType = FieldStorageType.SCALAR;
        f.isRequired = false;
        f.isUniqueField = false;
        f.isSearchable = false;
        f.isFilterable = false;
        f.isSortable = false;
        f.isListable = true;
        f.isDetailVisible = true;
        f.isEditable = true;
        f.isSystem = false;
        f.sortOrder = 0;
        return f;
    }

    public void applyMetadata(
            DpFieldGroup fieldGroup,
            String fieldName,
            FieldUiType uiType,
            FieldStorageType storageType,
            Boolean isRequired,
            Boolean isUniqueField,
            Boolean isSearchable,
            Boolean isFilterable,
            Boolean isSortable,
            Boolean isListable,
            Boolean isDetailVisible,
            Boolean isEditable,
            Integer sortOrder,
            Map<String, Object> defaultValue,
            Map<String, Object> validation,
            Map<String, Object> config,
            DpEntity relationEntity,
            RelationType relationType
    ) {
        this.fieldGroup = fieldGroup;
        this.fieldName = fieldName;
        this.uiType = uiType;
        this.storageType = storageType;
        this.isRequired = isRequired;
        this.isUniqueField = isUniqueField;
        this.isSearchable = isSearchable;
        this.isFilterable = isFilterable;
        this.isSortable = isSortable;
        this.isListable = isListable;
        this.isDetailVisible = isDetailVisible;
        this.isEditable = isEditable;
        this.sortOrder = sortOrder;
        this.defaultValue = defaultValue;
        this.validation = validation;
        this.config = config;
        this.relationEntity = relationEntity;
        this.relationType = relationType;
    }

    public void delete(String deletedBy) {
        if (Boolean.TRUE.equals(this.isSystem)) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "systemField", this.fieldCode);
        }
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "DpField", getId());
        }
        softDelete(deletedBy);
    }
}