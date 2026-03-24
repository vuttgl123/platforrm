package com.meta_forge_platform.definition.application.dto.entity.response;

import java.time.LocalDateTime;
import java.util.Map;

public record EntityResponse(
        Long id,
        Long moduleId,
        String moduleCode,
        String code,
        String name,
        String tableStrategy,
        String description,
        Boolean root,
        Boolean active,
        String displayNamePattern,
        Map<String, Object> defaultSort,
        Map<String, Object> config,
        Long versionNo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
