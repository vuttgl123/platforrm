package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dp_screen_field",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_screen_field_unique",
                columnNames = {"screen_id", "field_id", "section_id"}))
@SQLDelete(sql = "UPDATE dp_screen_field SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class DpScreenField extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "screen_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_screen_field_screen"))
    private DpScreen screen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", foreignKey = @ForeignKey(name = "fk_dp_screen_field_section"))
    private DpScreenSection section;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_screen_field_field"))
    private DpField field;

    @Column(name = "display_label", length = 255)
    private String displayLabel;

    @Column(name = "widget_type", length = 50)
    private String widgetType;

    @Column(name = "col_span", nullable = false)
    @Builder.Default
    private Integer colSpan = 12;

    @Column(name = "row_no", nullable = false)
    @Builder.Default
    private Integer rowNo = 0;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;

    @Column(name = "is_readonly", nullable = false)
    @Builder.Default
    private Boolean isReadonly = false;

    @Column(name = "is_hidden", nullable = false)
    @Builder.Default
    private Boolean isHidden = false;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> configJson;
}