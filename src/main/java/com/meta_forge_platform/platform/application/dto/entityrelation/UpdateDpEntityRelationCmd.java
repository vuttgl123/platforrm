package com.meta_forge_platform.platform.application.dto.entityrelation;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpEntityRelationCmd {
    @NotBlank @Size(max = 255) private String relationName;
    private String relationKind;
    private Long mappedByFieldId;
    private Map<String, Object> configJson;
    @NotNull private Long versionNo;
}