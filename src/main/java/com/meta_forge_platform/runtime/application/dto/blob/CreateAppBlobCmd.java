package com.meta_forge_platform.runtime.application.dto.blob;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateAppBlobCmd {
    private String blobCode;
    @NotBlank private String fileName;
    private String contentType;
    @NotNull @Min(0) private Long fileSize;
    @Builder.Default private String storageProvider = "LOCAL";
    @NotBlank private String storageKey;
    private String checksum;
    private Map<String, Object> metadataJson;
}