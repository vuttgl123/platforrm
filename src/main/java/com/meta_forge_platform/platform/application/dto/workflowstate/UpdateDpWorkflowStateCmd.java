package com.meta_forge_platform.platform.application.dto.workflowstate;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDpWorkflowStateCmd {

    @NotBlank
    @Size(max = 255)
    private String stateName;

    @NotBlank
    @Size(max = 30)
    private String stateType;

    @NotNull
    private Boolean isInitial;

    @NotNull
    private Boolean isFinal;

    @Size(max = 30)
    private String colorCode;

    @NotNull
    private Integer sortOrder;

    private Map<String, Object> configJson;

    @NotNull
    private Long versionNo;
}