package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.platform.domain.enumeration.ConstraintType;
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
@Table(name = "dp_entity_constraint",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_entity_constraint_code",
                columnNames = {"entity_id", "constraint_code"}))
public class DpEntityConstraint extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false)
    private DpEntity entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private DpField field;

    @Column(name = "constraint_code", nullable = false, length = 100)
    private String constraintCode;

    @Column(name = "constraint_name", nullable = false, length = 255)
    private String constraintName;

    @Enumerated(EnumType.STRING)
    @Column(name = "constraint_type", nullable = false)
    private ConstraintType constraintType;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "expression_json", nullable = false, columnDefinition = "JSON")
    private Map<String, Object> expression;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public static DpEntityConstraint create(
            DpEntity entity,
            DpField field,
            String code,
            String name,
            ConstraintType type,
            Map<String, Object> expression
    ) {
        if (entity == null) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "entity");
        }
        if (code == null || code.isBlank()) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "constraintCode");
        }
        if (name == null || name.isBlank()) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "constraintName");
        }
        if (type == null) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "constraintType");
        }
        if (expression == null) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "expressionJson");
        }

        DpEntityConstraint c = new DpEntityConstraint();
        c.entity = entity;
        c.field = field;
        c.constraintCode = code;
        c.constraintName = name;
        c.constraintType = type;
        c.expression = expression;
        c.isActive = true;
        return c;
    }

    public void applyMetadata(
            String name,
            ConstraintType type,
            Map<String, Object> expression,
            Boolean isActive
    ) {
        if (name == null || name.isBlank()) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "constraintName");
        }
        if (type == null) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "constraintType");
        }
        if (expression == null) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "expressionJson");
        }
        if (isActive == null) {
            throw AppException.of(ErrorCode.FIELD_REQUIRED, "isActive");
        }

        this.constraintName = name;
        this.constraintType = type;
        this.expression = expression;
        this.isActive = isActive;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "DpEntityConstraint", getId());
        }
        softDelete(deletedBy);
    }
}