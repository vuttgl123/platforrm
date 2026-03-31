package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dp_screen",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_screen_module_code",
                columnNames = {"module_id", "screen_code"}))
@SQLDelete(sql = "UPDATE dp_screen SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class DpScreen extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "module_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_dp_screen_module"))
    private DpModule module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", foreignKey = @ForeignKey(name = "fk_dp_screen_entity"))
    private DpEntity entity;

    @Column(name = "screen_code", nullable = false, length = 100)
    private String screenCode;

    @Column(name = "screen_name", nullable = false, length = 255)
    private String screenName;

    @Column(name = "screen_type", nullable = false, length = 50)
    private String screenType;

    @Column(name = "route_path", length = 255)
    private String routePath;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> configJson;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DpScreenSection> sections = new ArrayList<>();

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DpScreenField> screenFields = new ArrayList<>();
}