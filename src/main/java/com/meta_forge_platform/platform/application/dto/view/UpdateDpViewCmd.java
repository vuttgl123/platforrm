package com.meta_forge_platform.platform.application.dto.view;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpViewCmd {
    @NotBlank @Size(max = 255) private String viewName;
    private String viewType;
    private Map<String, Object> queryJson;
    private List<Object> columnsJson;
    private Boolean isDefault;
    private Boolean isActive;
    @NotNull private Long versionNo;
}