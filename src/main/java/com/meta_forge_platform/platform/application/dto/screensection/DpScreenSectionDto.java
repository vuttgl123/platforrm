package com.meta_forge_platform.platform.application.dto.screensection;

import com.meta_forge_platform.platform.application.dto.screen.DpScreenSummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class DpScreenSectionDto extends BaseDto {
    private DpScreenSummaryDto screen;
    private String sectionCode;
    private String sectionName;
    private String sectionType;
    private Long parentSectionId;
    private String parentSectionName;
    private Integer sortOrder;
    private Map<String, Object> configJson;
    private Boolean isDeleted;
}