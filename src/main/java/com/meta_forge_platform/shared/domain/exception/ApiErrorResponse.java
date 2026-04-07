package com.meta_forge_platform.shared.domain.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    private final String code;
    private final String message;
    private final Object meta;
    private final LocalDateTime timestamp;

    public static ApiErrorResponse of(String code, String message, Object meta) {
        return ApiErrorResponse.builder()
                .code(code)
                .message(message)
                .meta(meta)
                .timestamp(LocalDateTime.now())
                .build();
    }
}