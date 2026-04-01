package com.meta_forge_platform.platform.application.dto.entityrelation;

import com.meta_forge_platform.platform.application.dto.entity.DpEntitySummaryDto;
import com.meta_forge_platform.platform.application.dto.field.DpFieldSummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class DpEntityRelationDto extends BaseDto {
    private DpEntitySummaryDto sourceEntity;
    private String relationCode;
    private String relationName;
    private DpEntitySummaryDto targetEntity;
    private String relationKind;
    private DpFieldSummaryDto mappedByField;
    private Map<String, Object> configJson;
    private Boolean isDeleted;
}