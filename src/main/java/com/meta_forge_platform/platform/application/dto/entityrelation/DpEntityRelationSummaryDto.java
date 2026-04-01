package com.meta_forge_platform.platform.application.dto.entityrelation;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpEntityRelationSummaryDto {
    private Long id;
    private String relationCode;
    private String relationName;
    private String relationKind;
    private Long sourceEntityId;
    private Long targetEntityId;
}