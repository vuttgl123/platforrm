package com.meta_forge_platform.runtime.domain.entity;

import com.meta_forge_platform.platform.domain.entity.DpWorkflow;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowState;
import com.meta_forge_platform.platform.domain.entity.DpWorkflowTransition;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "app_record_state_history")
public class AppRecordStateHistory extends SoftDeletableEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "record_id", nullable = false)
    private AppRecord record;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_id", nullable = false)
    private DpWorkflow workflow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_state_id")
    private DpWorkflowState fromState;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_state_id", nullable = false)
    private DpWorkflowState toState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transition_id")
    private DpWorkflowTransition transition;

    @Column(name = "action_code", length = 100)
    private String actionCode;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    public static AppRecordStateHistory create(
            AppRecord record,
            DpWorkflow workflow,
            DpWorkflowState fromState,
            DpWorkflowState toState,
            DpWorkflowTransition transition,
            String actionCode,
            String note
    ) {
        AppRecordStateHistory history = new AppRecordStateHistory();
        history.record = record;
        history.workflow = workflow;
        history.fromState = fromState;
        history.toState = toState;
        history.transition = transition;
        history.actionCode = actionCode;
        history.note = note;
        history.changedAt = LocalDateTime.now();
        history.validateTopology();
        return history;
    }

    public void updateNote(String note) {
        this.note = note;
    }

    public void delete(String deletedBy) {
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "AppRecordStateHistory", getId());
        }
        softDelete(deletedBy);
    }

    private void validateTopology() {
        if (record == null || workflow == null || toState == null) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "STATE_HISTORY_INVALID");
        }

        Long workflowId = workflow.getId();

        if (workflowId == null) {
            return;
        }

        if (fromState != null
                && fromState.getWorkflow() != null
                && fromState.getWorkflow().getId() != null
                && !workflowId.equals(fromState.getWorkflow().getId())) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "STATE_HISTORY_FROM_STATE_WORKFLOW_MISMATCH");
        }

        if (toState.getWorkflow() != null
                && toState.getWorkflow().getId() != null
                && !workflowId.equals(toState.getWorkflow().getId())) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "STATE_HISTORY_TO_STATE_WORKFLOW_MISMATCH");
        }

        if (transition != null
                && transition.getWorkflow() != null
                && transition.getWorkflow().getId() != null
                && !workflowId.equals(transition.getWorkflow().getId())) {
            throw AppException.of(ErrorCode.BAD_REQUEST, "STATE_HISTORY_TRANSITION_WORKFLOW_MISMATCH");
        }
    }
}