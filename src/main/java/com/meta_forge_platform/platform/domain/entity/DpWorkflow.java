package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "dp_workflow",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_workflow_entity_code",
                columnNames = {"entity_id", "workflow_code"}
        )
)
public class DpWorkflow extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_id", nullable = false)
    private DpEntity entity;

    @Column(name = "workflow_code", nullable = false, length = 100)
    private String workflowCode;

    @Column(name = "workflow_name", nullable = false, length = 255)
    private String workflowName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> config;

    @Version
    @Column(name = "version_no", nullable = false)
    private Long versionNo;

    @OneToMany(mappedBy = "workflow", fetch = FetchType.LAZY)
    private List<DpWorkflowState> states = new ArrayList<>();

    @OneToMany(mappedBy = "workflow", fetch = FetchType.LAZY)
    private List<DpWorkflowTransition> transitions = new ArrayList<>();

    public static DpWorkflow create(
            DpEntity entity,
            String code,
            String name,
            String description
    ) {
        DpWorkflow workflow = new DpWorkflow();
        workflow.entity = entity;
        workflow.workflowCode = code;
        workflow.workflowName = name;
        workflow.description = description;
        workflow.isDefault = false;
        workflow.isActive = true;
        return workflow;
    }

    public void applyMetadata(
            String workflowName,
            String description,
            Boolean isDefault,
            Boolean isActive,
            Map<String, Object> config
    ) {
        this.workflowName = workflowName;
        this.description = description;
        this.isDefault = isDefault;
        this.isActive = isActive;
        this.config = config;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "DpWorkflow", getId());
        }
        softDelete(deletedBy);
    }
}