package com.meta_forge_platform.platform.application.dto.menu;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDpMenuCmd {

    @NotBlank
    @Size(max = 255)
    private String menuName;

    private Long parentMenuId;

    @NotBlank
    @Size(max = 30)
    private String menuType;

    private Long screenId;

    @Size(max = 255)
    private String routePath;

    @Size(max = 100)
    private String icon;

    @NotNull
    private Integer sortOrder;

    @NotNull
    private Boolean isActive;

    private Map<String, Object> configJson;

    @NotNull
    private Long versionNo;
}