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

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "level")
    private Integer level;

    @Column(name = "path", length = 500)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_type", nullable = false)
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
        m.isActive = true;
        return m;
    }

    public void assignParent(Long parentId, String parentPath) {
        this.parentId = parentId;
        this.path = parentPath + "/" + getId();
        this.level = path.split("/").length - 1;
    }

    public void updateRoute(String routePath) {
        this.routePath = routePath;
    }

    public void attachScreen(DpScreen screen) {
        this.screen = screen;
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