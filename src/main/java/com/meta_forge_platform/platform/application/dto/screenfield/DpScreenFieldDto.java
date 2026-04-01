package com.meta_forge_platform.platform.application.dto.screenfield;

import com.meta_forge_platform.platform.application.dto.field.DpFieldSummaryDto;
import com.meta_forge_platform.platform.application.dto.screen.DpScreenSummaryDto;
import com.meta_forge_platform.platform.application.dto.screensection.DpScreenSectionSummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class DpScreenFieldDto extends BaseDto {
    private DpScreenSummaryDto screen;
    private DpScreenSectionSummaryDto section;
    private DpFieldSummaryDto field;
    private String displayLabel;
    private String widgetType;
    private Integer colSpan;
    private Integer rowNo;
    private Integer sortOrder;
    private Boolean isReadonly;
    private Boolean isHidden;
    private Map<String, Object> configJson;
    private Boolean isDeleted;
}