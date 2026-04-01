package com.meta_forge_platform.platform.application.dto.entityconstraint;

import com.meta_forge_platform.platform.application.dto.entity.DpEntitySummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class DpEntityConstraintDto extends BaseDto {
    private DpEntitySummaryDto entity;
    private String constraintCode;
    private String constraintName;
    private String constraintType;
    private Map<String, Object> expressionJson;
    private Boolean isActive;
    private Boolean isDeleted;
}