package com.meta_forge_platform.definition.domain.model.workflow;

import com.meta_forge_platform.definition.domain.model.entity.EntityDefinition;
import com.meta_forge_platform.shared.domain.base.BaseActivableEntity;
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
        name = "dp_workflow",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dp_workflow_entity_code", columnNames = {"entity_id", "workflow_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class WorkflowDefinition extends BaseActivableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_workflow_entity"))
    private EntityDefinition entity;

    @Column(name = "workflow_code", nullable = false, length = 100)
    private String code;

    @Column(name = "workflow_name", nullable = false, length = 255)
    private String name;

    @Column(name = "description")
    private String description;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    public WorkflowDefinition(EntityDefinition entity, String code, String name) {
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
            throw new ValidationException("Workflow code must not be blank");
        }
        this.code = code.trim();
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Workflow name must not be blank");
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
