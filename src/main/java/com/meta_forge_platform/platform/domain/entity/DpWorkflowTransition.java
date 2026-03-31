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
@Table(name = "dp_workflow_transition",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_workflow_transition_code",
                columnNames = {"workflow_id", "transition_code"}))
@SQLDelete(sql = "UPDATE dp_workflow_transition SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class DpWorkflowTransition extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_workflow_transition_workflow"))
    private DpWorkflow workflow;

    @Column(name = "transition_code", nullable = false, length = 100)
    private String transitionCode;

    @Column(name = "transition_name", nullable = false, length = 255)
    private String transitionName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_state_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_workflow_transition_from_state"))
    private DpWorkflowState fromState;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_state_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_workflow_transition_to_state"))
    private DpWorkflowState toState;

    @Column(name = "action_code", length = 100)
    private String actionCode;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "condition_json", columnDefinition = "JSON")
    private Map<String, Object> conditionJson;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "effect_json", columnDefinition = "JSON")
    private Map<String, Object> effectJson;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;
}