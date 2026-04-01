package com.meta_forge_platform.platform.application.dto.screenfield;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateDpScreenFieldCmd {
    private String displayLabel;
    private String widgetType;
    private Integer colSpan;
    private Integer rowNo;
    private Integer sortOrder;
    private Boolean isReadonly;
    private Boolean isHidden;
    private Map<String, Object> configJson;
    @NotNull private Long versionNo;
}