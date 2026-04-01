package com.meta_forge_platform.platform.application.dto.menu;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDpMenuCmd {
    @NotNull private Long moduleId;
    @NotBlank @Size(max = 100) private String menuCode;
    @NotBlank @Size(max = 255) private String menuName;
    private Long parentMenuId;
    @Builder.Default private String menuType = "SCREEN";
    private Long screenId;
    private String routePath;
    private String icon;
    @Builder.Default private Integer sortOrder = 0;
    @Builder.Default private Boolean isActive = true;
    private Map<String, Object> configJson;
}