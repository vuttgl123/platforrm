package com.meta_forge_platform.platform.application.dto.entityconstraint;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDpEntityConstraintCmd {

    @NotNull
    private Long entityId;

    private Long fieldId;

    @NotBlank
    @Size(max = 100)
    private String constraintCode;

    @NotBlank
    @Size(max = 255)
    private String constraintName;

    @NotBlank
    @Size(max = 50)
    private String constraintType;

    @NotNull
    private Map<String, Object> expressionJson;

    @Builder.Default
    private Boolean isActive = true;
}