package com.meta_forge_platform.definition.domain.model.workflow;

import com.meta_forge_platform.shared.domain.base.BaseSortableEntity;
import com.meta_forge_platform.shared.domain.enumtype.WorkflowStateType;
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
        name = "dp_workflow_state",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dp_workflow_state_code", columnNames = {"workflow_id", "state_code"})
        }
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class WorkflowStateDefinition extends BaseSortableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_workflow_state_workflow"))
    private WorkflowDefinition workflow;

    @Column(name = "state_code", nullable = false, length = 100)
    private String code;

    @Column(name = "state_name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_type", nullable = false, length = 30)
    private WorkflowStateType stateType = WorkflowStateType.NORMAL;

    @Column(name = "is_initial", nullable = false)
    private Boolean initial = Boolean.FALSE;

    @Column(name = "is_final", nullable = false)
    private Boolean finalState = Boolean.FALSE;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "config_json", columnDefinition = "json")
    private Map<String, Object> config = new LinkedHashMap<>();

    public WorkflowStateDefinition(WorkflowDefinition workflow, String code, String name) {
        changeWorkflow(workflow);
        changeCode(code);
        changeName(name);
    }

    public void changeWorkflow(WorkflowDefinition workflow) {
        if (workflow == null) {
            throw new ValidationException("Workflow must not be null");
        }
        this.workflow = workflow;
    }

    public void changeCode(String code) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("State code must not be blank");
        }
        this.code = code.trim();
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("State name must not be blank");
        }
        this.name = name.trim();
    }

    public void changeStateType(WorkflowStateType stateType) {
        if (stateType == null) {
            throw new ValidationException("State type must not be null");
        }
        this.stateType = stateType;
    }

    public void markInitial() {
        this.initial = Boolean.TRUE;
    }

    public void unmarkInitial() {
        this.initial = Boolean.FALSE;
    }

    public void markFinal() {
        this.finalState = Boolean.TRUE;
    }

    public void unmarkFinal() {
        this.finalState = Boolean.FALSE;
    }

    public void changeConfig(Map<String, Object> config) {
        this.config = config == null ? new LinkedHashMap<>() : new LinkedHashMap<>(config);
    }
}
