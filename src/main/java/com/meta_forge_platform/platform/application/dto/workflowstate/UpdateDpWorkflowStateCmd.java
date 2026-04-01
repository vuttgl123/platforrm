package com.meta_forge_platform.platform.application.dto.workflowstate;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpWorkflowStateCmd {
    @NotBlank @Size(max = 255) private String stateName;
    private String stateType;
    private Boolean isInitial;
    private Boolean isFinal;
    private String colorCode;
    private Integer sortOrder;
    private Map<String, Object> configJson;
    @NotNull private Long versionNo;
}