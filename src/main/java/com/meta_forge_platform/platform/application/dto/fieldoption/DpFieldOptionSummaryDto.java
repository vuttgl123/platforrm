package com.meta_forge_platform.platform.application.dto.fieldoption;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpFieldOptionSummaryDto {
    private Long id;
    private String optionCode;
    private String optionLabel;
    private String optionValue;
    private String colorCode;
    private Integer sortOrder;
    private Boolean isDefault;
    private Boolean isActive;
}