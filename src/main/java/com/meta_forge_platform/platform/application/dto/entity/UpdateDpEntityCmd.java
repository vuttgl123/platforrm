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
public class UpdateDpEntityCmd {

    @NotBlank
    @Size(max = 255)
    private String entityName;

    @NotBlank
    @Size(max = 30)
    private String tableStrategy;

    private String description;

    @NotNull
    private Boolean isRoot;

    @NotNull
    private Boolean isActive;

    @Size(max = 500)
    private String displayNamePattern;

    private List<Object> defaultSortJson;

    private Map<String, Object> configJson;

    @NotNull
    private Long versionNo;
}