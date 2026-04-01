package com.meta_forge_platform.platform.application.dto.menu;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpMenuCmd {
    @NotBlank @Size(max = 255) private String menuName;
    private Long parentMenuId;
    private String menuType;
    private Long screenId;
    private String routePath;
    private String icon;
    private Integer sortOrder;
    private Boolean isActive;
    private Map<String, Object> configJson;
    @NotNull private Long versionNo;
}