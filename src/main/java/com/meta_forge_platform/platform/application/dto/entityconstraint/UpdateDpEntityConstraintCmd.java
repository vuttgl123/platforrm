package com.meta_forge_platform.platform.application.dto.entityconstraint;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpEntityConstraintCmd {
    @NotBlank @Size(max = 255) private String constraintName;
    private String constraintType;
    private Map<String, Object> expressionJson;
    private Boolean isActive;
    @NotNull private Long versionNo;
}