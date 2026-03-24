package com.meta_forge_platform.definition.domain.model.module;

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
        name = "dp_module",
        uniqueConstraints = @UniqueConstraint(name = "uk_dp_module_code", columnNames = "module_code")
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ModuleDefinition extends BaseVersionedEntity {

    @Column(name = "module_code", nullable = false, length = 100)
    private String code;

    @Column(name = "module_name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status", nullable = false, length = 30)
    private String status = "ACTIVE";

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Column(name = "is_system", nullable = false)
    private Boolean system = Boolean.FALSE;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    public ModuleDefinition(String code, String name, String description) {
        changeCode(code);
        changeName(name);
        this.description = description;
    }

    public void changeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("module.code", "validation.not_blank");
        }
        this.code = code.trim().toUpperCase();
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("module.name", "validation.not_blank"
            );
        }
        this.name = name.trim();
    }

    public void activate() { this.status = "ACTIVE"; }

    public void deactivate() { this.status = "INACTIVE"; }

    public boolean isActive() { return "ACTIVE".equals(this.status); }

    public void changeConfig(Map<String, Object> config) {
        this.config = config == null ? new LinkedHashMap<>() : new LinkedHashMap<>(config);
    }
}