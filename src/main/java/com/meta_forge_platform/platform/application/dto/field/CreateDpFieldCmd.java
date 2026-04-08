package com.meta_forge_platform.platform.application.dto.field;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDpFieldCmd {

    @NotNull
    private Long entityId;

    private Long fieldGroupId;

    @NotBlank
    @Size(max = 100)
    private String fieldCode;

    @NotBlank
    @Size(max = 255)
    private String fieldName;

    @NotBlank
    @Size(max = 50)
    private String dataType;

    @Size(max = 50)
    private String uiType;

    @Builder.Default
    @NotBlank
    @Size(max = 30)
    private String storageType = "SCALAR";

    @Builder.Default
    @NotNull
    private Boolean isRequired = false;

    @Builder.Default
    @NotNull
    private Boolean isUniqueField = false;

    @Builder.Default
    @NotNull
    private Boolean isSearchable = false;

    @Builder.Default
    @NotNull
    private Boolean isFilterable = false;

    @Builder.Default
    @NotNull
    private Boolean isSortable = false;

    @Builder.Default
    @NotNull
    private Boolean isListable = true;

    @Builder.Default
    @NotNull
    private Boolean isDetailVisible = true;

    @Builder.Default
    @NotNull
    private Boolean isEditable = true;

    @Builder.Default
    @NotNull
    private Integer sortOrder = 0;

    private Map<String, Object> defaultValueJson;

    private Map<String, Object> validationJson;

    private Map<String, Object> configJson;

    private Long relationEntityId;

    @Size(max = 30)
    private String relationType;
}