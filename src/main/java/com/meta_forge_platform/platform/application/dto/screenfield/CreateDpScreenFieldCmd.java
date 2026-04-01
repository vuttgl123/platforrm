package com.meta_forge_platform.platform.application.dto.screenfield;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDpScreenFieldCmd {
    @NotNull private Long screenId;
    private Long sectionId;
    @NotNull private Long fieldId;
    private String displayLabel;
    private String widgetType;
    @Builder.Default private Integer colSpan = 12;
    @Builder.Default private Integer rowNo = 0;
    @Builder.Default private Integer sortOrder = 0;
    @Builder.Default private Boolean isReadonly = false;
    @Builder.Default private Boolean isHidden = false;
    private Map<String, Object> configJson;
}