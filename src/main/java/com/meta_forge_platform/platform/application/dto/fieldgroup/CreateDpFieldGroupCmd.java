package com.meta_forge_platform.platform.application.dto.fieldgroup;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDpFieldGroupCmd {

    @NotNull
    private Long entityId;

    @NotBlank
    @Size(max = 100)
    private String groupCode;

    @NotBlank
    @Size(max = 255)
    private String groupName;

    @Builder.Default
    @NotNull
    private Integer sortOrder = 0;

    @Builder.Default
    @NotBlank
    @Size(max = 30)
    private String layoutType = "VERTICAL";

    private Map<String, Object> configJson;

    @Builder.Default
    @NotNull
    private Boolean isActive = true;
}