package com.meta_forge_platform.platform.application.dto.screen;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpScreenCmd {
    @NotBlank @Size(max = 255) private String screenName;
    private String screenType;
    private String routePath;
    private Boolean isActive;
    private Integer sortOrder;
    private Map<String, Object> configJson;
    @NotNull private Long versionNo;
}