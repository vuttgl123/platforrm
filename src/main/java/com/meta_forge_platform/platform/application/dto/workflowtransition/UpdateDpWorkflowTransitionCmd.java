package com.meta_forge_platform.platform.application.dto.workflowtransition;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDpWorkflowTransitionCmd {

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

    @NotNull
    private Boolean isActive;

    @NotNull
    private Integer sortOrder;

    @NotNull
    private Long versionNo;
}