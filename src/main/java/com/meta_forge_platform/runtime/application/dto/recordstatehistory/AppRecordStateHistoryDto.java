package com.meta_forge_platform.runtime.application.dto.recordstatehistory;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRecordStateHistoryDto {

    private Long id;

    private Long recordId;
    private String recordCode;
    private String recordDisplayName;

    private Long workflowId;
    private String workflowCode;
    private String workflowName;

    private Long fromStateId;
    private String fromStateCode;
    private String fromStateName;

    private Long toStateId;
    private String toStateCode;
    private String toStateName;
    private String toStateColor;

    private Long transitionId;
    private String transitionCode;
    private String transitionName;

    private String actionCode;
    private String note;
    private LocalDateTime changedAt;

    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}