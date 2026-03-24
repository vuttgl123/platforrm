package com.meta_forge_platform.definition.domain.model.workflow;

import com.meta_forge_platform.shared.domain.base.BaseVersionedActivableSortableEntity;
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
        name = "dp_workflow_transition",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dp_workflow_transition_code", columnNames = {"workflow_id", "transition_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class WorkflowTransitionDefinition extends BaseVersionedActivableSortableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_workflow_transition_workflow"))
    private WorkflowDefinition workflow;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_state_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_workflow_transition_from"))
    private WorkflowStateDefinition fromState;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_state_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_workflow_transition_to"))
    private WorkflowStateDefinition toState;

    @Column(name = "transition_code", nullable = false, length = 100)
    private String code;

    @Column(name = "transition_name", nullable = false, length = 255)
    private String name;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "condition_json", columnDefinition = "json")
    private Map<String, Object> condition = new LinkedHashMap<>();

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "action_json", columnDefinition = "json")
    private Map<String, Object> action = new LinkedHashMap<>();

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    public WorkflowTransitionDefinition(
            WorkflowDefinition workflow,
            WorkflowStateDefinition fromState,
            WorkflowStateDefinition toState,
            String code,
            String name
    ) {
        changeWorkflow(workflow);
        changeFromState(fromState);
        changeToState(toState);
        changeCode(code);
        changeName(name);
    }

    public void changeWorkflow(WorkflowDefinition workflow) {
        if (workflow == null) {
            throw new ValidationException("Workflow must not be null");
        }
        this.workflow = workflow;
    }

    public void changeFromState(WorkflowStateDefinition fromState) {
        if (fromState == null) {
            throw new ValidationException("From state must not be null");
        }
        this.fromState = fromState;
    }

    public void changeToState(WorkflowStateDefinition toState) {
        if (toState == null) {
            throw new ValidationException("To state must not be null");
        }
        this.toState = toState;
    }

    public void changeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Transition code must not be blank");
        }
        this.code = code.trim();
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Transition name must not be blank");
        }
        this.name = name.trim();
    }

    public void changeCondition(Map<String, Object> condition) {
        this.condition = condition == null ? new LinkedHashMap<>() : new LinkedHashMap<>(condition);
    }

    public void changeAction(Map<String, Object> action) {
        this.action = action == null ? new LinkedHashMap<>() : new LinkedHashMap<>(action);
    }

    public void changeConfig(Map<String, Object> config) {
        this.config = config == null ? new LinkedHashMap<>() : new LinkedHashMap<>(config);
    }
}
