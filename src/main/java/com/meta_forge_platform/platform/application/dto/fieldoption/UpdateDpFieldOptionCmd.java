package com.meta_forge_platform.platform.application.dto.fieldoption;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpFieldOptionCmd {
    @NotBlank @Size(max = 255) private String optionLabel;
    @Size(max = 255) private String optionValue;
    private String colorCode;
    private Integer sortOrder;
    private Boolean isDefault;
    private Boolean isActive;
    private Map<String, Object> configJson;
    @NotNull private Long versionNo;
}