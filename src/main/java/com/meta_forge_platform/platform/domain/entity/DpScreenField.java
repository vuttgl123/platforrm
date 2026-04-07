package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.platform.domain.enumeration.ScreenWidgetType;
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
@Table(
        name = "dp_screen_field",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_screen_field_unique",
                columnNames = {"screen_id", "field_id", "section_id"}
        )
)
public class DpScreenField extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "screen_id", nullable = false)
    private DpScreen screen;

    @Column(name = "section_id")
    private Long sectionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", nullable = false)
    private DpField field;

    @Column(name = "display_label", length = 255)
    private String displayLabel;

    @Enumerated(EnumType.STRING)
    @Column(name = "widget_type", length = 50)
    private ScreenWidgetType widgetType;

    @Column(name = "col_span", nullable = false)
    private Integer colSpan;

    @Column(name = "row_no", nullable = false)
    private Integer rowNo;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "is_readonly", nullable = false)
    private Boolean isReadonly;

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> config;

    public static DpScreenField create(DpScreen screen, DpField field) {
        DpScreenField screenField = new DpScreenField();
        screenField.screen = screen;
        screenField.field = field;
        screenField.colSpan = 12;
        screenField.rowNo = 0;
        screenField.sortOrder = 0;
        screenField.isReadonly = false;
        screenField.isHidden = false;
        return screenField;
    }

    public void assignSection(Long sectionId) {
        this.sectionId = sectionId;
    }

    public void updateDisplay(String displayLabel, ScreenWidgetType widgetType, Integer colSpan) {
        this.displayLabel = displayLabel;
        this.widgetType = widgetType;
        this.colSpan = colSpan;
    }

    public void reorder(Integer rowNo, Integer sortOrder) {
        this.rowNo = rowNo;
        this.sortOrder = sortOrder;
    }

    public void hide() {
        this.isHidden = true;
    }

    public void show() {
        this.isHidden = false;
    }

    public void markReadonly() {
        this.isReadonly = true;
    }

    public void markEditable() {
        this.isReadonly = false;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, getId());
        }
        softDelete(deletedBy);
    }
}