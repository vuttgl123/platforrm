package com.meta_forge_platform.definition.domain.model.field;

import com.meta_forge_platform.definition.domain.model.entity.EntityDefinition;
import com.meta_forge_platform.shared.domain.base.BaseSortableEntity;
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
        name = "dp_field_group",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dp_field_group_entity_code", columnNames = {"entity_id", "group_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class FieldGroupDefinition extends BaseSortableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_field_group_entity"))
    private EntityDefinition entity;

    @Column(name = "group_code", nullable = false, length = 100)
    private String code;

    @Column(name = "group_name", nullable = false, length = 255)
    private String name;

    @Column(name = "description")
    private String description;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    public FieldGroupDefinition(EntityDefinition entity, String code, String name) {
        changeEntity(entity);
        changeCode(code);
        changeName(name);
    }

    public void changeEntity(EntityDefinition entity) {
        if (entity == null) {
            throw new ValidationException("Entity must not be null");
        }
        this.entity = entity;
    }

    public void changeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Field group code must not be blank");
        }
        this.code = code.trim();
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Field group name must not be blank");
        }
        this.name = name.trim();
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeConfig(Map<String, Object> config) {
        this.config = config == null ? new LinkedHashMap<>() : new LinkedHashMap<>(config);
    }
}
