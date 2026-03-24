package com.meta_forge_platform.definition.domain.model.screen;

import com.meta_forge_platform.definition.domain.model.field.FieldDefinition;
import com.meta_forge_platform.shared.domain.base.BaseSortableEntity;
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
        name = "dp_screen_field",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dp_screen_field_unique", columnNames = {"section_id", "field_id"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ScreenFieldDefinition extends BaseSortableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "section_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_screen_field_section"))
    private ScreenSectionDefinition section;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_screen_field_field"))
    private FieldDefinition field;

    @Column(name = "col_span", nullable = false)
    private Integer colSpan = 1;

    @Column(name = "row_span", nullable = false)
    private Integer rowSpan = 1;

    @Column(name = "override_label", length = 255)
    private String overrideLabel;

    @Column(name = "is_readonly", nullable = false)
    private Boolean readonly = Boolean.FALSE;

    @Column(name = "is_hidden", nullable = false)
    private Boolean hidden = Boolean.FALSE;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    public ScreenFieldDefinition(ScreenSectionDefinition section, FieldDefinition field) {
        changeSection(section);
        changeField(field);
    }

    public void changeSection(ScreenSectionDefinition section) {
        if (section == null) {
            throw new ValidationException("Section must not be null");
        }
        this.section = section;
    }

    public void changeField(FieldDefinition field) {
        if (field == null) {
            throw new ValidationException("Field must not be null");
        }
        this.field = field;
    }

    public void changeColSpan(Integer colSpan) {
        if (colSpan == null || colSpan <= 0) {
            throw new ValidationException("Col span must be > 0");
        }
        this.colSpan = colSpan;
    }

    public void changeRowSpan(Integer rowSpan) {
        if (rowSpan == null || rowSpan <= 0) {
            throw new ValidationException("Row span must be > 0");
        }
        this.rowSpan = rowSpan;
    }

    public void changeOverrideLabel(String overrideLabel) {
        this.overrideLabel = overrideLabel;
    }

    public void markReadonly() {
        this.readonly = Boolean.TRUE;
    }

    public void markEditable() {
        this.readonly = Boolean.FALSE;
    }

    public void hide() {
        this.hidden = Boolean.TRUE;
    }

    public void show() {
        this.hidden = Boolean.FALSE;
    }

    public void changeConfig(Map<String, Object> config) {
        this.config = config == null ? new LinkedHashMap<>() : new LinkedHashMap<>(config);
    }
}
