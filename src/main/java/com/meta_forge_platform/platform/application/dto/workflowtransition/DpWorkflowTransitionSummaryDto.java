package com.meta_forge_platform.platform.application.dto.workflowtransition;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpWorkflowTransitionSummaryDto {
    private Long id;
    private String transitionCode;
    private String transitionName;
    private String actionCode;
    private Long fromStateId;
    private String fromStateName;
    private Long toStateId;
    private String toStateName;
    private Boolean isActive;
}