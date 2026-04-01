package com.meta_forge_platform.platform.application.dto.workflow;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpWorkflowCmd {
    @NotBlank @Size(max = 255) private String workflowName;
    private String description;
    private Boolean isDefault;
    private Boolean isActive;
    private Map<String, Object> configJson;
    @NotNull private Long versionNo;
}