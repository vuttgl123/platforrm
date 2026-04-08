package com.meta_forge_platform.platform.application.dto.view;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDpViewCmd {

    @NotBlank
    @Size(max = 255)
    private String viewName;

    @NotBlank
    @Size(max = 30)
    private String viewType;

    @NotNull
    private Map<String, Object> queryJson;

    private List<Object> columnsJson;

    @NotBlank
    @Size(max = 30)
    private String visibility;

    @NotNull
    private Integer sortOrder;

    @NotNull
    private Boolean isDefault;

    @NotNull
    private Boolean isActive;

    @NotNull
    private Long versionNo;
}