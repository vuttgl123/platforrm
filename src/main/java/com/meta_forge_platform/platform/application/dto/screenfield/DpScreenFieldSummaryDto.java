package com.meta_forge_platform.platform.application.dto.screenfield;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpScreenFieldSummaryDto {
    private Long id;
    private Long screenId;
    private Long sectionId;
    private Long fieldId;
    private String fieldCode;
    private String fieldName;
    private String displayLabel;
    private String widgetType;
    private Integer colSpan;
    private Integer rowNo;
    private Integer sortOrder;
    private Boolean isReadonly;
    private Boolean isHidden;
}