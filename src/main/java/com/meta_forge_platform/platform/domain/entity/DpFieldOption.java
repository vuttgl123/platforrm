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
@Table(name = "dp_field_option",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_field_option_code",
                columnNames = {"field_id", "option_code"}))
@SQLDelete(sql = "UPDATE dp_field_option SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class DpFieldOption extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_field_option_field"))
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
    @Builder.Default
    private Integer sortOrder = 0;

    @Column(name = "is_default", nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> configJson;
}