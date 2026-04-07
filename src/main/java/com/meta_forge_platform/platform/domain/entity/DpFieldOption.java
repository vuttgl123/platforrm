package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.platform.domain.entity.DpField;
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
@Table(name = "dp_field_option",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_field_option_code",
                columnNames = {"field_id", "option_code"}))
public class DpFieldOption extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", nullable = false)
    private DpField field;

    @Column(name = "option_code", nullable = false, length = 100)
    private String optionCode;

    @Column(name = "option_label", nullable = false, length = 255)
    private String optionLabel;

    @Column(name = "option_value", nullable = false, length = 255)
    private String optionValue;

    @Column(name = "color_code", length = 30)
    private String colorCode;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> config;

    public static DpFieldOption create(
            DpField field,
            String code,
            String label,
            String value
    ) {
        DpFieldOption o = new DpFieldOption();
        o.field = field;
        o.optionCode = code;
        o.optionLabel = label;
        o.optionValue = value;
        o.sortOrder = 0;
        o.isDefault = false;
        o.isActive = true;
        return o;
    }

    public void update(String label, String value, String color) {
        this.optionLabel = label;
        this.optionValue = value;
        this.colorCode = color;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, getId());
        }
        softDelete(deletedBy);
    }
}