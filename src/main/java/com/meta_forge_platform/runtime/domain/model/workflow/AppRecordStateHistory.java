package com.meta_forge_platform.runtime.domain.model.workflow;

import com.meta_forge_platform.definition.domain.model.workflow.WorkflowStateDefinition;
import com.meta_forge_platform.definition.domain.model.workflow.WorkflowTransitionDefinition;
import com.meta_forge_platform.runtime.domain.model.record.AppRecord;
import com.meta_forge_platform.shared.domain.base.BaseEntity;
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
@Table(name = "app_record_state_history")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AppRecordStateHistory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_state_history_record"))
    private AppRecord record;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_state_id", foreignKey = @ForeignKey(name = "fk_app_record_state_history_from"))
    private WorkflowStateDefinition fromState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_state_id", foreignKey = @ForeignKey(name = "fk_app_record_state_history_to"))
    private WorkflowStateDefinition toState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transition_id", foreignKey = @ForeignKey(name = "fk_app_record_state_history_transition"))
    private WorkflowTransitionDefinition transition;

    @Column(name = "comment_text")
    private String commentText;

    @Convert(converter = JsonMapConverter.class)
    @Column(name = "metadata_json", columnDefinition = "json")
    private Map<String, Object> metadata = new LinkedHashMap<>();
}
