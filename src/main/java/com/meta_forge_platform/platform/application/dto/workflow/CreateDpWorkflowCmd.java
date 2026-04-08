package com.meta_forge_platform.platform.application.dto.workflow;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDpWorkflowCmd {

    @NotNull
    private Long entityId;

    @NotBlank
    @Size(max = 100)
    private String workflowCode;

    @NotBlank
    @Size(max = 255)
    private String workflowName;

    private String description;

    @Builder.Default
    @NotNull
    private Boolean isDefault = true;

    @Builder.Default
    @NotNull
    private Boolean isActive = true;

    private Map<String, Object> configJson;
}