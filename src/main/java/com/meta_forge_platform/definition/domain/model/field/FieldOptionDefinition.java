package com.meta_forge_platform.definition.domain.model.field;

import com.meta_forge_platform.shared.domain.base.BaseActivableEntity;
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
        name = "dp_field_option",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dp_field_option_field_code", columnNames = {"field_id", "option_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class FieldOptionDefinition extends BaseActivableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_field_option_field"))
    private FieldDefinition field;

    @Column(name = "option_code", nullable = false, length = 100)
    private String code;

    @Column(name = "option_label", nullable = false, length = 255)
    private String label;

    @Column(name = "option_value", nullable = false, length = 255)
    private String value;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Column(name = "is_default", nullable = false)
    private Boolean defaultOption = Boolean.FALSE;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    public FieldOptionDefinition(FieldDefinition field, String code, String label, String value) {
        changeField(field);
        changeCode(code);
        changeLabel(label);
        changeValue(value);
    }

    public void changeField(FieldDefinition field) {
        if (field == null) {
            throw new ValidationException("Field must not be null");
        }
        this.field = field;
    }

    public void changeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Option code must not be blank");
        }
        this.code = code.trim();
    }

    public void changeLabel(String label) {
        if (label == null || label.isBlank()) {
            throw new ValidationException("Option label must not be blank");
        }
        this.label = label.trim();
    }

    public void changeValue(String value) {
        if (value == null || value.isBlank()) {
            throw new ValidationException("Option value must not be blank");
        }
        this.value = value.trim();
    }

    public void changeSortOrder(Integer sortOrder) {
        if (sortOrder == null || sortOrder < 0) {
            throw new ValidationException("Sort order must be >= 0");
        }
        this.sortOrder = sortOrder;
    }

    public void markDefault() {
        this.defaultOption = Boolean.TRUE;
    }

    public void unmarkDefault() {
        this.defaultOption = Boolean.FALSE;
    }

    public void changeConfig(Map<String, Object> config) {
        this.config = config == null ? new LinkedHashMap<>() : new LinkedHashMap<>(config);
    }
}
