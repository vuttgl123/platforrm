package com.meta_forge_platform.platform.application.dto.fieldoption;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDpFieldOptionCmd {
    @NotNull private Long fieldId;
    @NotBlank @Size(max = 100) private String optionCode;
    @NotBlank @Size(max = 255) private String optionLabel;
    @NotBlank @Size(max = 255) private String optionValue;
    private String colorCode;
    @Builder.Default private Integer sortOrder = 0;
    @Builder.Default private Boolean isDefault = false;
    @Builder.Default private Boolean isActive = true;
    private Map<String, Object> configJson;
}