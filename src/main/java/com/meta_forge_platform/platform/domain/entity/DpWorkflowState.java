package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.platform.domain.enumeration.WorkflowStateType;
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
        name = "dp_workflow_state",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_workflow_state_code",
                columnNames = {"workflow_id", "state_code"}
        )
)
public class DpWorkflowState extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_id", nullable = false)
    private DpWorkflow workflow;

    @Column(name = "state_code", nullable = false, length = 100)
    private String stateCode;

    @Column(name = "state_name", nullable = false, length = 255)
    private String stateName;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_type", nullable = false, length = 30)
    private WorkflowStateType stateType;

    @Column(name = "is_initial", nullable = false)
    private Boolean isInitial;

    @Column(name = "is_final", nullable = false)
    private Boolean isFinal;

    @Column(name = "color_code", length = 30)
    private String colorCode;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> config;

    public static DpWorkflowState create(
            DpWorkflow workflow,
            String code,
            String name
    ) {
        DpWorkflowState state = new DpWorkflowState();
        state.workflow = workflow;
        state.stateCode = code;
        state.stateName = name;
        state.stateType = WorkflowStateType.NORMAL;
        state.isInitial = false;
        state.isFinal = false;
        state.sortOrder = 0;
        return state;
    }

    public void applyMetadata(
            String stateName,
            WorkflowStateType stateType,
            Boolean isInitial,
            Boolean isFinal,
            String colorCode,
            Integer sortOrder,
            Map<String, Object> config
    ) {
        this.stateName = stateName;
        this.stateType = stateType;
        this.isInitial = isInitial;
        this.isFinal = isFinal;
        this.colorCode = colorCode;
        this.sortOrder = sortOrder;
        this.config = config;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "DpWorkflowState", getId());
        }
        softDelete(deletedBy);
    }
}