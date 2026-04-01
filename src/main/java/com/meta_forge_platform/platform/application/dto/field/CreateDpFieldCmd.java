package com.meta_forge_platform.platform.application.dto.field;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDpFieldCmd {
    @NotNull private Long entityId;
    private Long fieldGroupId;
    @NotBlank @Size(max = 100) private String fieldCode;
    @NotBlank @Size(max = 255) private String fieldName;
    @NotBlank private String dataType;
    private String uiType;
    @Builder.Default private String storageType = "SCALAR";
    @Builder.Default private Boolean isRequired = false;
    @Builder.Default private Boolean isUniqueField = false;
    @Builder.Default private Boolean isSearchable = false;
    @Builder.Default private Boolean isFilterable = false;
    @Builder.Default private Boolean isSortable = false;
    @Builder.Default private Boolean isListable = true;
    @Builder.Default private Boolean isDetailVisible = true;
    @Builder.Default private Boolean isEditable = true;
    @Builder.Default private Integer sortOrder = 0;
    private Map<String, Object> defaultValueJson;
    private Map<String, Object> validationJson;
    private Map<String, Object> configJson;
    private Long relationEntityId;
    private String relationType;
}