package com.meta_forge_platform.shared.interfaces.rest;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        String code,
        String message,
        LocalDateTime timestamp,
        Map<String, String> details
) {
    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message, LocalDateTime.now(), Map.of());
    }

    public static ErrorResponse of(String code, String message, Map<String, String> details) {
        return new ErrorResponse(code, message, LocalDateTime.now(), details);
    }
}
