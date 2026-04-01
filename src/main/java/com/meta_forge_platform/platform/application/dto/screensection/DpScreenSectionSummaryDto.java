package com.meta_forge_platform.platform.application.dto.screensection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpScreenSectionSummaryDto {
    private Long id;
    private String sectionCode;
    private String sectionName;
    private String sectionType;
    private Long parentSectionId;
    private Integer sortOrder;
    private Long screenId;
}