package com.meta_forge_platform.runtime.application.dto.blob;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateAppBlobCmd {
    private String fileName;
    private Map<String, Object> metadataJson;
    @NotNull private Long versionNo;
}