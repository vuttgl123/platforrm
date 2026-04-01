package com.meta_forge_platform.runtime.application.dto.record;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateAppRecordCmd {
    @NotNull private Long entityId;
    private String recordCode;
    private String displayName;
    private Long parentRecordId;
    private Long rootRecordId;
    @Builder.Default private String status = "ACTIVE";
    private Map<String, Object> dataJson;
}