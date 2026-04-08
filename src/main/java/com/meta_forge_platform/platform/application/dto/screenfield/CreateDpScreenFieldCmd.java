package com.meta_forge_platform.platform.application.dto.screenfield;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDpScreenFieldCmd {

    @NotNull
    private Long screenId;

    private Long sectionId;

    @NotNull
    private Long fieldId;

    @Size(max = 255)
    private String displayLabel;

    @Size(max = 50)
    private String widgetType;

    @Builder.Default
    @NotNull
    private Integer colSpan = 12;

    @Builder.Default
    @NotNull
    private Integer rowNo = 0;

    @Builder.Default
    @NotNull
    private Integer sortOrder = 0;

    @Builder.Default
    @NotNull
    private Boolean isReadonly = false;

    @Builder.Default
    @NotNull
    private Boolean isHidden = false;

    private Map<String, Object> configJson;
}