package com.meta_forge_platform.runtime.application.dto.record;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class AvailableTransitionDto {
    private Long transitionId;
    private String transitionCode;
    private String transitionName;
    private String actionCode;
    private Long toStateId;
    private String toStateName;
    private String toStateColor;
}