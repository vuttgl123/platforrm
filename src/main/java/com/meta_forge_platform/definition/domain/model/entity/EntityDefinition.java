package com.meta_forge_platform.definition.domain.model.entity;

import com.meta_forge_platform.definition.domain.model.module.ModuleDefinition;
import com.meta_forge_platform.shared.domain.base.BaseVersionedEntity;
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
        name = "dp_entity",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dp_entity_module_code", columnNames = {"module_id", "entity_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class EntityDefinition extends BaseVersionedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "module_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_dp_entity_module")
    )
    private ModuleDefinition module;

    @Column(name = "entity_code", nullable = false, length = 100)
    private String code;

    @Column(name = "entity_name", nullable = false, length = 255)
    private String name;

    @Column(name = "table_strategy", nullable = false, length = 30)
    private String tableStrategy = "GENERIC";

    @Column(name = "description")
    private String description;

    @Column(name = "is_root", nullable = false)
    private Boolean root = Boolean.TRUE;

    @Column(name = "is_active", nullable = false)
    private Boolean active = Boolean.TRUE;

    @Column(name = "display_name_pattern", length = 500)
    private String displayNamePattern;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "default_sort_json", columnDefinition = "json")
    private Map<String, Object> defaultSort = new LinkedHashMap<>();

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    public EntityDefinition(
            ModuleDefinition module,
            String code,
            String name,
            String description
    ) {
        changeModule(module);
        changeCode(code);
        changeName(name);
        this.description = description;
    }

    public void changeModule(ModuleDefinition module) {
        if (module == null) {
            throw new ValidationException("Module must not be null");
        }
        this.module = module;
    }

    public void changeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Entity code must not be blank");
        }
        this.code = code.trim();
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Entity name must not be blank");
        }
        this.name = name.trim();
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeTableStrategy(String tableStrategy) {
        if (tableStrategy == null || tableStrategy.isBlank()) {
            throw new ValidationException("Table strategy must not be blank");
        }
        this.tableStrategy = tableStrategy.trim();
    }

    public void changeDisplayNamePattern(String displayNamePattern) {
        this.displayNamePattern = displayNamePattern;
    }

    public void changeDefaultSort(Map<String, Object> defaultSort) {
        this.defaultSort = defaultSort == null ? new LinkedHashMap<>() : new LinkedHashMap<>(defaultSort);
    }

    public void changeConfig(Map<String, Object> config) {
        this.config = config == null ? new LinkedHashMap<>() : new LinkedHashMap<>(config);
    }

    public void markAsRoot() {
        this.root = Boolean.TRUE;
    }

    public void markAsNonRoot() {
        this.root = Boolean.FALSE;
    }

    public boolean isRoot() {
        return Boolean.TRUE.equals(root);
    }

    public void activate() {
        this.active = Boolean.TRUE;
    }

    public void deactivate() {
        this.active = Boolean.FALSE;
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(active);
    }
}
