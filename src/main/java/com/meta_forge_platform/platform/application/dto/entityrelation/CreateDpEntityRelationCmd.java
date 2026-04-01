package com.meta_forge_platform.platform.application.dto.entityrelation;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDpEntityRelationCmd {
    @NotNull private Long sourceEntityId;
    @NotBlank @Size(max = 100) private String relationCode;
    @NotBlank @Size(max = 255) private String relationName;
    @NotNull private Long targetEntityId;
    @NotBlank private String relationKind;
    private Long mappedByFieldId;
    private Map<String, Object> configJson;
}