package com.meta_forge_platform.platform.application.dto.screensection;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDpScreenSectionCmd {

    @NotNull
    private Long screenId;

    @NotBlank
    @Size(max = 100)
    private String sectionCode;

    @NotBlank
    @Size(max = 255)
    private String sectionName;

    @NotBlank
    @Size(max = 50)
    private String sectionType;

    private Long parentSectionId;

    @Builder.Default
    @NotNull
    private Integer sortOrder = 0;

    private Map<String, Object> configJson;
}