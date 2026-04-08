package com.meta_forge_platform.platform.domain.entity;

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
@Table(
        name = "dp_workflow_transition",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_workflow_transition_code",
                columnNames = {"workflow_id", "transition_code"}
        )
)
public class DpWorkflowTransition extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_id", nullable = false)
    private DpWorkflow workflow;

    @Column(name = "transition_code", nullable = false, length = 100)
    private String transitionCode;

    @Column(name = "transition_name", nullable = false, length = 255)
    private String transitionName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_state_id", nullable = false)
    private DpWorkflowState fromState;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_state_id", nullable = false)
    private DpWorkflowState toState;

    @Column(name = "action_code", length = 100)
    private String actionCode;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "condition_json", columnDefinition = "JSON")
    private Map<String, Object> condition;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "effect_json", columnDefinition = "JSON")
    private Map<String, Object> effect;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    public static DpWorkflowTransition create(
            DpWorkflow workflow,
            String code,
            String name,
            DpWorkflowState fromState,
            DpWorkflowState toState
    ) {
        DpWorkflowTransition transition = new DpWorkflowTransition();
        transition.workflow = workflow;
        transition.transitionCode = code;
        transition.transitionName = name;
        transition.fromState = fromState;
        transition.toState = toState;
        transition.isActive = true;
        transition.sortOrder = 0;
        transition.validateTopology();
        return transition;
    }

    public void applyMetadata(
            String transitionName,
            DpWorkflowState fromState,
            DpWorkflowState toState,
            String actionCode,
            Map<String, Object> condition,
            Map<String, Object> effect,
            Boolean isActive,
            Integer sortOrder
    ) {
        this.transitionName = transitionName;
        this.fromState = fromState;
        this.toState = toState;
        this.actionCode = actionCode;
        this.condition = condition;
        this.effect = effect;
        this.isActive = isActive;
        this.sortOrder = sortOrder;
        validateTopology();
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "DpWorkflowTransition", getId());
        }
        softDelete(deletedBy);
    }

    private void validateTopology() {
        if (workflow == null || fromState == null || toState == null) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "WORKFLOW_TRANSITION_INVALID");
        }

        if (workflow.getId() == null
                || fromState.getWorkflow() == null
                || fromState.getWorkflow().getId() == null
                || toState.getWorkflow() == null
                || toState.getWorkflow().getId() == null) {
            return;
        }

        if (!workflow.getId().equals(fromState.getWorkflow().getId())
                || !workflow.getId().equals(toState.getWorkflow().getId())) {
            throw AppException.of(ErrorCode.TRANSITION_INVALID, transitionCode);
        }
    }
}