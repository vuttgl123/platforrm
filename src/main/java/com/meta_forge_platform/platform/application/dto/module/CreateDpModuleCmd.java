package com.meta_forge_platform.platform.application.dto.module;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDpModuleCmd {
    @NotBlank(message = "Module code không được để trống")
    @Size(max = 100) private String moduleCode;

    @NotBlank(message = "Module name không được để trống")
    @Size(max = 255) private String moduleName;

    private String description;
    @Builder.Default private String status = "ACTIVE";
    @Builder.Default private Integer sortOrder = 0;
    @Builder.Default private Boolean isSystem = false;
}