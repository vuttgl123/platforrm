package com.meta_forge_platform.platform.application.dto.module;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDpModuleCmd {

    @NotBlank
    @Size(max = 100)
    private String moduleCode;

    @NotBlank
    @Size(max = 255)
    private String moduleName;

    private String description;

    @Builder.Default
    @NotBlank
    @Size(max = 30)
    private String status = "ACTIVE";

    @Builder.Default
    @NotNull
    private Integer sortOrder = 0;

    @Builder.Default
    @NotNull
    private Boolean isSystem = false;
}