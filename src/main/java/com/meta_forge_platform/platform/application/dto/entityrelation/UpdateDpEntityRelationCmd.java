package com.meta_forge_platform.platform.application.dto.entityrelation;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDpEntityRelationCmd {

    @NotBlank
    @Size(max = 255)
    private String relationName;

    @NotBlank
    @Size(max = 30)
    private String relationType;

    private Long ownerFieldId;

    private Long inverseFieldId;

    private Map<String, Object> configJson;

    @NotNull
    private Boolean isActive;

    @NotNull
    private Long versionNo;
}