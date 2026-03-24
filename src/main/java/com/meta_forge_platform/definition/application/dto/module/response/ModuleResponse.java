package com.meta_forge_platform.definition.application.dto.module.response;

import java.time.LocalDateTime;

public record ModuleResponse(
        Long id,
        String code,
        String name,
        String description,
        String status,
        Integer sortOrder,
        Boolean system,
        Long versionNo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
