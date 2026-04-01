package com.meta_forge_platform.platform.application.dto.workflowtransition;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDpWorkflowTransitionCmd {
    @NotNull private Long workflowId;
    @NotBlank @Size(max = 100) private String transitionCode;
    @NotBlank @Size(max = 255) private String transitionName;
    @NotNull private Long fromStateId;
    @NotNull private Long toStateId;
    private String actionCode;
    private Map<String, Object> conditionJson;
    private Map<String, Object> effectJson;
    @Builder.Default private Boolean isActive = true;
    @Builder.Default private Integer sortOrder = 0;
}