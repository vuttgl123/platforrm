package com.meta_forge_platform.platform.application.dto.fieldoption;

import com.meta_forge_platform.platform.application.dto.field.DpFieldSummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class DpFieldOptionDto extends BaseDto {
    private DpFieldSummaryDto field;
    private String optionCode;
    private String optionLabel;
    private String optionValue;
    private String colorCode;
    private Integer sortOrder;
    private Boolean isDefault;
    private Boolean isActive;
    private Map<String, Object> configJson;
    private Boolean isDeleted;
}