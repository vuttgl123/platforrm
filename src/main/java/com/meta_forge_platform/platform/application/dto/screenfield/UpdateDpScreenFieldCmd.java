package com.meta_forge_platform.platform.application.dto.screenfield;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDpScreenFieldCmd {

    private Long sectionId;

    @Size(max = 255)
    private String displayLabel;

    @Size(max = 50)
    private String widgetType;

    @NotNull
    private Integer colSpan;

    @NotNull
    private Integer rowNo;

    @NotNull
    private Integer sortOrder;

    @NotNull
    private Boolean isReadonly;

    @NotNull
    private Boolean isHidden;

    private Map<String, Object> configJson;

    @NotNull
    private Long versionNo;
}