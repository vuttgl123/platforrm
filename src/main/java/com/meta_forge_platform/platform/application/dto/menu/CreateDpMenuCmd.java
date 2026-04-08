package com.meta_forge_platform.platform.application.dto.menu;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDpMenuCmd {

    @NotNull
    private Long moduleId;

    @NotBlank
    @Size(max = 100)
    private String menuCode;

    @NotBlank
    @Size(max = 255)
    private String menuName;

    private Long parentMenuId;

    @Builder.Default
    @NotBlank
    @Size(max = 30)
    private String menuType = "SCREEN";

    private Long screenId;

    @Size(max = 255)
    private String routePath;

    @Size(max = 100)
    private String icon;

    @Builder.Default
    @NotNull
    private Integer sortOrder = 0;

    @Builder.Default
    @NotNull
    private Boolean isActive = true;

    private Map<String, Object> configJson;
}