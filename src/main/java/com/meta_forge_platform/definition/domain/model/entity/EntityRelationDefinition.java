package com.meta_forge_platform.definition.domain.model.entity;

import com.meta_forge_platform.shared.domain.base.BaseActivableEntity;
import com.meta_forge_platform.shared.domain.enumtype.RelationKind;
import com.meta_forge_platform.shared.domain.enumtype.RelationType;
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
        name = "dp_entity_relation",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dp_entity_relation_code", columnNames = {"source_entity_id", "relation_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class EntityRelationDefinition extends BaseActivableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_entity_relation_source"))
    private EntityDefinition sourceEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_entity_relation_target"))
    private EntityDefinition targetEntity;

    @Column(name = "relation_code", nullable = false, length = 100)
    private String code;

    @Column(name = "relation_name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "relation_type", nullable = false, length = 30)
    private RelationType relationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "relation_kind", nullable = false, length = 30)
    private RelationKind relationKind = RelationKind.ASSOCIATION;

    @Column(name = "mapped_by_field", length = 100)
    private String mappedByField;

    @Column(name = "inverse_field", length = 100)
    private String inverseField;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    public EntityRelationDefinition(
            EntityDefinition sourceEntity,
            EntityDefinition targetEntity,
            String code,
            String name,
            RelationType relationType
    ) {
        changeSourceEntity(sourceEntity);
        changeTargetEntity(targetEntity);
        changeCode(code);
        changeName(name);
        changeRelationType(relationType);
    }

    public void changeSourceEntity(EntityDefinition sourceEntity) {
        if (sourceEntity == null) {
            throw new ValidationException("Source entity must not be null");
        }
        this.sourceEntity = sourceEntity;
    }

    public void changeTargetEntity(EntityDefinition targetEntity) {
        if (targetEntity == null) {
            throw new ValidationException("Target entity must not be null");
        }
        this.targetEntity = targetEntity;
    }

    public void changeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Relation code must not be blank");
        }
        this.code = code.trim();
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Relation name must not be blank");
        }
        this.name = name.trim();
    }

    public void changeRelationType(RelationType relationType) {
        if (relationType == null) {
            throw new ValidationException("Relation type must not be null");
        }
        this.relationType = relationType;
    }

    public void changeRelationKind(RelationKind relationKind) {
        if (relationKind == null) {
            throw new ValidationException("Relation kind must not be null");
        }
        this.relationKind = relationKind;
    }

    public void changeMappedByField(String mappedByField) {
        this.mappedByField = mappedByField;
    }

    public void changeInverseField(String inverseField) {
        this.inverseField = inverseField;
    }

    public void changeConfig(Map<String, Object> config) {
        this.config = config == null ? new LinkedHashMap<>() : new LinkedHashMap<>(config);
    }
}
