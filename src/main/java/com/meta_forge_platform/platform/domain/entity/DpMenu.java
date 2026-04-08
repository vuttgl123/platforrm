package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.platform.domain.enumeration.MenuType;
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
@Table(name = "dp_menu",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_menu_module_code",
                columnNames = {"module_id", "menu_code"}))
public class DpMenu extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "module_id", nullable = false)
    private DpModule module;

    @Column(name = "menu_code", nullable = false, length = 100)
    private String menuCode;

    @Column(name = "menu_name", nullable = false, length = 255)
    private String menuName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_menu_id")
    private DpMenu parentMenu;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "path", length = 500)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_type", nullable = false, length = 30)
    private MenuType menuType;

    @Column(name = "route_path", length = 255)
    private String routePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private DpScreen screen;

    @Column(name = "icon", length = 100)
    private String icon;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> config;

    public static DpMenu create(
            DpModule module,
            String code,
            String name,
            MenuType type
    ) {
        DpMenu m = new DpMenu();
        m.module = module;
        m.menuCode = code;
        m.menuName = name;
        m.menuType = type;
        m.sortOrder = 0;
        m.level = 0;
        m.path = "/";
        m.isActive = true;
        return m;
    }

    public void applyMetadata(
            String menuName,
            DpMenu parentMenu,
            MenuType menuType,
            DpScreen screen,
            String routePath,
            String icon,
            Integer sortOrder,
            Boolean isActive,
            Map<String, Object> config
    ) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
        this.menuType = menuType;
        this.screen = screen;
        this.routePath = routePath;
        this.icon = icon;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
        this.config = config;
    }

    public void applyHierarchy() {
        if (this.parentMenu == null) {
            this.level = 0;
            this.path = "/";
            return;
        }

        Integer parentLevel = this.parentMenu.getLevel() == null ? 0 : this.parentMenu.getLevel();
        this.level = parentLevel + 1;

        String parentPath = this.parentMenu.getPath();
        if (parentPath == null || parentPath.isBlank()) {
            parentPath = "/";
        }

        this.path = parentPath + this.parentMenu.getId() + "/";
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "DpMenu", getId());
        }
        softDelete(deletedBy);
    }
}