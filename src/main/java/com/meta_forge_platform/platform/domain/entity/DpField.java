package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.platform.domain.entity.DpEntity;
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
import lombok.Setter;

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

    @Column(name = "field_code", nullable = false, length = 100)
    private String fieldCode;

    @Column(name = "field_name", nullable = false, length = 255)
    private String fieldName;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false)
    private FieldDataType dataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ui_type")
    private FieldUiType uiType;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type", nullable = false)
    private FieldStorageType storageType;

    @Embedded
    private FieldBehavior behavior;

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
    @Column(name = "relation_type")
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
        f.behavior = FieldBehavior.defaultBehavior();
        return f;
    }

    public void updateBasic(String name, FieldUiType uiType) {
        this.fieldName = name;
        this.uiType = uiType;
    }

    public void attachRelation(DpEntity target, RelationType type) {
        this.relationEntity = target;
        this.relationType = type;
        this.storageType = FieldStorageType.RELATION;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, getId());
        }
        softDelete(deletedBy);
    }

    @Embeddable
    @Getter
    @Setter
    public static class FieldBehavior {

        private Boolean required;
        private Boolean unique;
        private Boolean searchable;
        private Boolean filterable;
        private Boolean sortable;
        private Boolean listable;
        private Boolean detailVisible;
        private Boolean editable;

        public static FieldBehavior defaultBehavior() {
            FieldBehavior b = new FieldBehavior();
            b.required = false;
            b.unique = false;
            b.searchable = false;
            b.filterable = false;
            b.sortable = false;
            b.listable = true;
            b.detailVisible = true;
            b.editable = true;
            return b;
        }
    }
}