package com.meta_forge_platform.platform.application.dto.entity;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpEntityCmd {
    @NotBlank @Size(max = 255) private String entityName;
    private String tableStrategy;
    private String description;
    private Boolean isRoot;
    private Boolean isActive;
    private String displayNamePattern;
    private List<Object> defaultSortJson;
    private Map<String, Object> configJson;
    @NotNull private Long versionNo;
}