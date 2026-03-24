package com.meta_forge_platform.definition.domain.model.menu;

import com.meta_forge_platform.definition.domain.model.module.ModuleDefinition;
import com.meta_forge_platform.definition.domain.model.screen.ScreenDefinition;
import com.meta_forge_platform.shared.domain.base.BaseActivableEntity;
import com.meta_forge_platform.shared.domain.enumtype.MenuType;
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
        name = "dp_menu",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dp_menu_module_code", columnNames = {"module_id", "menu_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MenuDefinition extends BaseActivableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "module_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_menu_module"))
    private ModuleDefinition module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_dp_menu_parent"))
    private MenuDefinition parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", foreignKey = @ForeignKey(name = "fk_dp_menu_screen"))
    private ScreenDefinition screen;

    @Column(name = "menu_code", nullable = false, length = 100)
    private String code;

    @Column(name = "menu_name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_type", nullable = false, length = 30)
    private MenuType menuType;

    @Column(name = "icon", length = 100)
    private String icon;

    @Column(name = "path", length = 500)
    private String path;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    public MenuDefinition(ModuleDefinition module, String code, String name, MenuType menuType) {
        changeModule(module);
        changeCode(code);
        changeName(name);
        changeMenuType(menuType);
    }

    public void changeModule(ModuleDefinition module) {
        if (module == null) {
            throw new ValidationException("Module must not be null");
        }
        this.module = module;
    }

    public void changeParent(MenuDefinition parent) {
        this.parent = parent;
    }

    public void changeScreen(ScreenDefinition screen) {
        this.screen = screen;
    }

    public void changeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Menu code must not be blank");
        }
        this.code = code.trim();
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Menu name must not be blank");
        }
        this.name = name.trim();
    }

    public void changeMenuType(MenuType menuType) {
        if (menuType == null) {
            throw new ValidationException("Menu type must not be null");
        }
        this.menuType = menuType;
    }

    public void changeIcon(String icon) {
        this.icon = icon;
    }

    public void changePath(String path) {
        this.path = path;
    }

    public void changeSortOrder(Integer sortOrder) {
        if (sortOrder == null || sortOrder < 0) {
            throw new ValidationException("Sort order must be >= 0");
        }
        this.sortOrder = sortOrder;
    }

    public void changeConfig(Map<String, Object> config) {
        this.config = config == null ? new LinkedHashMap<>() : new LinkedHashMap<>(config);
    }
}
