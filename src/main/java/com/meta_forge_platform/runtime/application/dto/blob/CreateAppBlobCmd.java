package com.meta_forge_platform.runtime.application.dto.blob;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAppBlobCmd {

    @Size(max = 100)
    private String blobCode;

    @NotBlank
    @Size(max = 500)
    private String fileName;

    @Size(max = 255)
    private String contentType;

    @NotNull
    @Min(0)
    private Long fileSize;

    @Builder.Default
    @NotBlank
    @Size(max = 50)
    private String storageProvider = "LOCAL";

    @NotBlank
    @Size(max = 1000)
    private String storageKey;

    @Size(max = 255)
    private String checksum;

    private Map<String, Object> metadataJson;
}