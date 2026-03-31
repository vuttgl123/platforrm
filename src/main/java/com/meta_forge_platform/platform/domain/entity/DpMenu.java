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
@Table(name = "dp_menu",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_menu_module_code",
                columnNames = {"module_id", "menu_code"}))
@SQLDelete(sql = "UPDATE dp_menu SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class DpMenu extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "module_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_menu_module"))
    private DpModule module;

    @Column(name = "menu_code", nullable = false, length = 100)
    private String menuCode;

    @Column(name = "menu_name", nullable = false, length = 255)
    private String menuName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_menu_id", foreignKey = @ForeignKey(name = "fk_dp_menu_parent"))
    private DpMenu parentMenu;

    @Column(name = "menu_type", nullable = false, length = 30)
    @Builder.Default
    private String menuType = "SCREEN";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", foreignKey = @ForeignKey(name = "fk_dp_menu_screen"))
    private DpScreen screen;

    @Column(name = "route_path", length = 255)
    private String routePath;

    @Column(name = "icon", length = 100)
    private String icon;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> configJson;

    @OneToMany(mappedBy = "parentMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DpMenu> children = new ArrayList<>();
}