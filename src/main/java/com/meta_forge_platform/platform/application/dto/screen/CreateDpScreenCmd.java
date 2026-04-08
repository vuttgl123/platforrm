package com.meta_forge_platform.platform.application.dto.screen;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDpScreenCmd {

    @NotNull
    private Long moduleId;

    private Long entityId;

    @NotBlank
    @Size(max = 100)
    private String screenCode;

    @NotBlank
    @Size(max = 255)
    private String screenName;

    @NotBlank
    @Size(max = 50)
    private String screenType;

    @Size(max = 255)
    private String routePath;

    @Builder.Default
    @NotNull
    private Boolean isActive = true;

    @Builder.Default
    @NotNull
    private Integer sortOrder = 0;

    private Map<String, Object> configJson;
}