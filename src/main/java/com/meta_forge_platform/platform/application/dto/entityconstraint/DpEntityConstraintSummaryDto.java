package com.meta_forge_platform.platform.application.dto.entityconstraint;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpEntityConstraintSummaryDto {
    private Long id;
    private String constraintCode;
    private String constraintName;
    private String constraintType;
    private Boolean isActive;
    private Long entityId;
}