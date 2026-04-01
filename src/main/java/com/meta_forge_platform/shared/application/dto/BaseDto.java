package com.meta_forge_platform.shared.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long versionNo;
    private String createdBy;
    private String updatedBy;
}