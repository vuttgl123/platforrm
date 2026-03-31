package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dp_entity_constraint",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_entity_constraint_code",
                columnNames = {"entity_id", "constraint_code"}))
@SQLDelete(sql = "UPDATE dp_entity_constraint SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class DpEntityConstraint extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_entity_constraint_entity"))
    private DpEntity entity;

    @Column(name = "constraint_code", nullable = false, length = 100)
    private String constraintCode;

    @Column(name = "constraint_name", nullable = false, length = 255)
    private String constraintName;

    @Column(name = "constraint_type", nullable = false, length = 30)
    private String constraintType;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "expression_json", nullable = false, columnDefinition = "JSON")
    private Map<String, Object> expressionJson;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}