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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private DpScreenSection section;

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

    @Version
    @Column(name = "version_no", nullable = false)
    private Long versionNo;

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

    public void applyMetadata(
            DpScreenSection section,
            String displayLabel,
            ScreenWidgetType widgetType,
            Integer colSpan,
            Integer rowNo,
            Integer sortOrder,
            Boolean isReadonly,
            Boolean isHidden,
            Map<String, Object> config
    ) {
        this.section = section;
        this.displayLabel = displayLabel;
        this.widgetType = widgetType;
        this.colSpan = colSpan;
        this.rowNo = rowNo;
        this.sortOrder = sortOrder;
        this.isReadonly = isReadonly;
        this.isHidden = isHidden;
        this.config = config;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "DpScreenField", getId());
        }
        softDelete(deletedBy);
    }
}