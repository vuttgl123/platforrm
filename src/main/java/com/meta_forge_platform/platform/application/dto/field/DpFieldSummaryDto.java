package com.meta_forge_platform.platform.application.dto.field;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DpFieldSummaryDto {
    private Long id;
    private String fieldCode;
    private String fieldName;
    private String dataType;
    private String uiType;
    private String storageType;
    private Boolean isRequired;
    private Boolean isSystem;
    private Integer sortOrder;
    private Long entityId;
    private Long fieldGroupId;
}