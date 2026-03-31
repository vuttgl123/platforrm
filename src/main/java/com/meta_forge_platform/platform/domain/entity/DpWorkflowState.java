package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.infrastructure.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dp_workflow_state",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_dp_workflow_state_code",
                columnNames = {"workflow_id", "state_code"}))
@SQLDelete(sql = "UPDATE dp_workflow_state SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class DpWorkflowState extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_id", nullable = false, foreignKey = @ForeignKey(name = "fk_dp_workflow_state_workflow"))
    private DpWorkflow workflow;

    @Column(name = "state_code", nullable = false, length = 100)
    private String stateCode;

    @Column(name = "state_name", nullable = false, length = 255)
    private String stateName;

    @Column(name = "state_type", nullable = false, length = 30)
    @Builder.Default
    private String stateType = "NORMAL";

    @Column(name = "is_initial", nullable = false)
    @Builder.Default
    private Boolean isInitial = false;

    @Column(name = "is_final", nullable = false)
    @Builder.Default
    private Boolean isFinal = false;

    @Column(name = "color_code", length = 30)
    private String colorCode;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;

    @Convert(converter = JsonConverter.MapConverter.class)
    @Column(name = "config_json", columnDefinition = "JSON")
    private Map<String, Object> configJson;

    @OneToMany(mappedBy = "fromState")
    @Builder.Default
    private List<DpWorkflowTransition> outgoingTransitions = new ArrayList<>();

    @OneToMany(mappedBy = "toState")
    @Builder.Default
    private List<DpWorkflowTransition> incomingTransitions = new ArrayList<>();
}