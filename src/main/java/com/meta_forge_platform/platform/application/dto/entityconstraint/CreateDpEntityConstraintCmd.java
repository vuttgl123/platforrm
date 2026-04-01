package com.meta_forge_platform.platform.application.dto.entityconstraint;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDpEntityConstraintCmd {
    @NotNull private Long entityId;
    @NotBlank @Size(max = 100) private String constraintCode;
    @NotBlank @Size(max = 255) private String constraintName;
    @NotBlank private String constraintType;
    @NotNull private Map<String, Object> expressionJson;
    @Builder.Default private Boolean isActive = true;
}