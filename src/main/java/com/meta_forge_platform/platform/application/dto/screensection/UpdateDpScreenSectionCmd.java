package com.meta_forge_platform.platform.application.dto.screensection;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpScreenSectionCmd {
    @NotBlank @Size(max = 255) private String sectionName;
    private String sectionType;
    private Long parentSectionId;
    private Integer sortOrder;
    private Map<String, Object> configJson;
    @NotNull private Long versionNo;
}