package com.meta_forge_platform.platform.application.dto.view;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDpViewCmd {

    @NotNull
    private Long entityId;

    @NotBlank
    @Size(max = 100)
    private String viewCode;

    @NotBlank
    @Size(max = 255)
    private String viewName;

    @Builder.Default
    @NotBlank
    @Size(max = 30)
    private String viewType = "LIST";

    @NotNull
    private Map<String, Object> queryJson;

    private List<Object> columnsJson;

    @Builder.Default
    @NotBlank
    @Size(max = 30)
    private String visibility = "MODULE";

    @Builder.Default
    @NotNull
    private Integer sortOrder = 0;

    @Builder.Default
    @NotNull
    private Boolean isDefault = false;

    @Builder.Default
    @NotNull
    private Boolean isActive = true;
}