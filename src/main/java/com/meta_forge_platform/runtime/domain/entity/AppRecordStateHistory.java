package com.meta_forge_platform.runtime.domain.entity;

import com.meta_forge_platform.platform.domain.entity.DpWorkflow;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowState;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowTransition;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_record_state_history")
@SQLDelete(sql = "UPDATE app_record_state_history SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class AppRecordStateHistory extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_state_history_record"))
    private AppRecord record;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_state_history_workflow"))
    private DpWorkflow workflow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_state_id", foreignKey = @ForeignKey(name = "fk_app_record_state_history_from_state"))
    private DpWorkflowState fromState;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_state_id", nullable = false, foreignKey = @ForeignKey(name = "fk_app_record_state_history_to_state"))
    private DpWorkflowState toState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transition_id", foreignKey = @ForeignKey(name = "fk_app_record_state_history_transition"))
    private DpWorkflowTransition transition;

    @Column(name = "action_code", length = 100)
    private String actionCode;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "changed_at", nullable = false)
    @Builder.Default
    private LocalDateTime changedAt = LocalDateTime.now();
}