package com.meta_forge_platform.platform.application.dto.fieldoption;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDpFieldOptionCmd {

    @NotBlank
    @Size(max = 255)
    private String optionLabel;

    @NotBlank
    @Size(max = 255)
    private String optionValue;

    @Size(max = 30)
    private String colorCode;

    @NotNull
    private Integer sortOrder;

    @NotNull
    private Boolean isDefault;

    @NotNull
    private Boolean isActive;

    private Map<String, Object> configJson;

    @NotNull
    private Long versionNo;
}