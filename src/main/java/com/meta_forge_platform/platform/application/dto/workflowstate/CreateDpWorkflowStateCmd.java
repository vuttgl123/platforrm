package com.meta_forge_platform.platform.application.dto.workflowstate;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDpWorkflowStateCmd {
    @NotNull private Long workflowId;
    @NotBlank @Size(max = 100) private String stateCode;
    @NotBlank @Size(max = 255) private String stateName;
    @Builder.Default private String stateType = "NORMAL";
    @Builder.Default private Boolean isInitial = false;
    @Builder.Default private Boolean isFinal = false;
    private String colorCode;
    @Builder.Default private Integer sortOrder = 0;
    private Map<String, Object> configJson;
}