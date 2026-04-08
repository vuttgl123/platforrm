package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.platform.domain.enumeration.RelationType;
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
@Table(name = "dp_entity_relation",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_entity_relation_code",
                columnNames = {"source_entity_id", "relation_code"}))
public class DpEntityRelation extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_entity_id", nullable = false)
    private DpEntity sourceEntity;

    @Column(name = "relation_code", nullable = false, length = 100)
    private String relationCode;

    @Column(name = "relation_name", nullable = false, length = 255)
    private String relationName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_entity_id", nullable = false)
    private DpEntity targetEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "relation_type", nullable = false)
    private RelationType relationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_field_id")
    private DpField ownerField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inverse_field_id")
    private DpField inverseField;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> config;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Version
    @Column(name = "version_no", nullable = false)
    private Long versionNo;

    public static DpEntityRelation create(
            DpEntity source,
            DpEntity target,
            String code,
            String name,
            RelationType type
    ) {
        if (source == null || target == null) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "entity");
        }

        DpEntityRelation r = new DpEntityRelation();
        r.sourceEntity = source;
        r.targetEntity = target;
        r.relationCode = code;
        r.relationName = name;
        r.relationType = type;
        r.isActive = true;
        return r;
    }

    public void applyMetadata(
            String relationName,
            RelationType relationType,
            DpField ownerField,
            DpField inverseField,
            Map<String, Object> config,
            Boolean isActive
    ) {
        this.relationName = relationName;
        this.relationType = relationType;
        this.ownerField = ownerField;
        this.inverseField = inverseField;
        this.config = config;
        this.isActive = isActive;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "DpEntityRelation", getId());
        }
        softDelete(deletedBy);
    }
}