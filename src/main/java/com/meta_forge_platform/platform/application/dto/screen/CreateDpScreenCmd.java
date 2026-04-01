package com.meta_forge_platform.platform.application.dto.screen;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDpScreenCmd {
    @NotNull private Long moduleId;
    private Long entityId;
    @NotBlank @Size(max = 100) private String screenCode;
    @NotBlank @Size(max = 255) private String screenName;
    @NotBlank private String screenType;
    private String routePath;
    @Builder.Default private Boolean isActive = true;
    @Builder.Default private Integer sortOrder = 0;
    private Map<String, Object> configJson;
}