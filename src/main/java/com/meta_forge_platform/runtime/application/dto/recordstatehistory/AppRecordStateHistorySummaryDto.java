package com.meta_forge_platform.runtime.application.dto.recordstatehistory;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRecordStateHistorySummaryDto {

    private Long id;
    private Long recordId;
    private Long workflowId;
    private Long fromStateId;
    private String fromStateName;
    private Long toStateId;
    private String toStateName;
    private String toStateColor;
    private String actionCode;
    private LocalDateTime changedAt;
}