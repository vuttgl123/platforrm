package com.meta_forge_platform.platform.application.dto.field;

import com.meta_forge_platform.platform.application.dto.entity.DpEntitySummaryDto;
import com.meta_forge_platform.platform.application.dto.fieldgroup.DpFieldGroupSummaryDto;
import com.meta_forge_platform.shared.application.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class DpFieldDto extends BaseDto {
    private DpEntitySummaryDto entity;
    private DpFieldGroupSummaryDto fieldGroup;
    private String fieldCode;
    private String fieldName;
    private String dataType;
    private String uiType;
    private String storageType;
    private Boolean isRequired;
    private Boolean isUniqueField;
    private Boolean isSearchable;
    private Boolean isFilterable;
    private Boolean isSortable;
    private Boolean isListable;
    private Boolean isDetailVisible;
    private Boolean isEditable;
    private Boolean isSystem;
    private Integer sortOrder;
    private Map<String, Object> defaultValueJson;
    private Map<String, Object> validationJson;
    private Map<String, Object> configJson;
    private DpEntitySummaryDto relationEntity;
    private String relationType;
    private Boolean isDeleted;
}