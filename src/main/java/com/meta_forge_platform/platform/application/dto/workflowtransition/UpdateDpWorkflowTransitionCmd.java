package com.meta_forge_platform.platform.application.dto.workflowtransition;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpWorkflowTransitionCmd {
    @NotBlank @Size(max = 255) private String transitionName;
    private Long fromStateId;
    private Long toStateId;
    private String actionCode;
    private Map<String, Object> conditionJson;
    private Map<String, Object> effectJson;
    private Boolean isActive;
    private Integer sortOrder;
    @NotNull private Long versionNo;
}