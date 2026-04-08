package com.meta_forge_platform.platform.application.dto.workflowtransition;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDpWorkflowTransitionCmd {

    @NotNull
    private Long workflowId;

    @NotBlank
    @Size(max = 100)
    private String transitionCode;

    @NotBlank
    @Size(max = 255)
    private String transitionName;

    @NotNull
    private Long fromStateId;

    @NotNull
    private Long toStateId;

    @Size(max = 100)
    private String actionCode;

    private Map<String, Object> conditionJson;

    private Map<String, Object> effectJson;

    @Builder.Default
    @NotNull
    private Boolean isActive = true;

    @Builder.Default
    @NotNull
    private Integer sortOrder = 0;
}