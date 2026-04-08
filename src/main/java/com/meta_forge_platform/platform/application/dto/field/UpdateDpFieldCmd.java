package com.meta_forge_platform.platform.application.dto.field;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDpFieldCmd {

    private Long fieldGroupId;

    @NotBlank
    @Size(max = 255)
    private String fieldName;

    @Size(max = 50)
    private String uiType;

    @NotBlank
    @Size(max = 30)
    private String storageType;

    @NotNull
    private Boolean isRequired;

    @NotNull
    private Boolean isUniqueField;

    @NotNull
    private Boolean isSearchable;

    @NotNull
    private Boolean isFilterable;

    @NotNull
    private Boolean isSortable;

    @NotNull
    private Boolean isListable;

    @NotNull
    private Boolean isDetailVisible;

    @NotNull
    private Boolean isEditable;

    @NotNull
    private Integer sortOrder;

    private Map<String, Object> defaultValueJson;

    private Map<String, Object> validationJson;

    private Map<String, Object> configJson;

    private Long relationEntityId;

    @Size(max = 30)
    private String relationType;

    @NotNull
    private Long versionNo;
}