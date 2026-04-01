package com.meta_forge_platform.runtime.application.dto.record;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AppRecordSummaryDto {
    private Long id;
    private String recordCode;
    private String displayName;
    private String status;
    private Long entityId;
    private String entityName;
    private Long currentStateId;
    private String currentStateName;
    private String currentStateColor;
}