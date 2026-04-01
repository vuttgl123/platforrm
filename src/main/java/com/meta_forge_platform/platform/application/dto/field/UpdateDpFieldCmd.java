package com.meta_forge_platform.platform.application.dto.field;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpFieldCmd {
    @NotBlank @Size(max = 255) private String fieldName;
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
    private Integer sortOrder;
    private Map<String, Object> defaultValueJson;
    private Map<String, Object> validationJson;
    private Map<String, Object> configJson;
    private Long relationEntityId;
    private String relationType;
    @NotNull private Long versionNo;
}