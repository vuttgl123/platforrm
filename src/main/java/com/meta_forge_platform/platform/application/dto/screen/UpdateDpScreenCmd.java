package com.meta_forge_platform.platform.application.dto.screen;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDpScreenCmd {

    @NotBlank
    @Size(max = 255)
    private String screenName;

    @NotBlank
    @Size(max = 50)
    private String screenType;

    @Size(max = 255)
    private String routePath;

    @NotNull
    private Boolean isActive;

    @NotNull
    private Integer sortOrder;

    private Map<String, Object> configJson;

    @NotNull
    private Long versionNo;
}