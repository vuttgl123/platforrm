package com.meta_forge_platform.platform.application.dto.module;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDpModuleCmd {

    @NotBlank
    @Size(max = 255)
    private String moduleName;

    private String description;

    @NotBlank
    @Size(max = 30)
    private String status;

    @NotNull
    private Integer sortOrder;

    @NotNull
    private Boolean isSystem;

    @NotNull
    private Long versionNo;
}