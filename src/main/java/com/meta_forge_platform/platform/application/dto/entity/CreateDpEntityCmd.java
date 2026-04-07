package com.meta_forge_platform.platform.application.dto.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDpEntityCmd {
    @NotNull
    private Long moduleId;

    @NotBlank
    @Size(max = 100)
    private String entityCode;

    @NotBlank
    @Size(max = 255)
    private String entityName;

    @Builder.Default
    private String tableStrategy = "GENERIC";

    private String description;

    @Builder.Default
    private Boolean isRoot = true;

    @Builder.Default
    private Boolean isActive = true;

    private String displayNamePattern;

    private List<Object> defaultSortJson;

    private Map<String, Object> configJson;
}