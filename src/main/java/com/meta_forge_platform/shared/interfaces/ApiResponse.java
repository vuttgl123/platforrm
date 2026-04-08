package com.meta_forge_platform.shared.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;
    private PageMeta meta;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> of(ErrorCode errorCode, String message, T data, PageMeta meta) {
        return ApiResponse.<T>builder()
                .code(errorCode.getHttpStatus().value())
                .message(message)
                .data(data)
                .meta(meta)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data, PageMeta meta) {
        return of(ErrorCode.SUCCESS, message, data, meta);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return success(message, data, null);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode, String message) {
        return of(errorCode, message, null, null);
    }
    public ResponseEntity<ApiResponse<T>> toResponseEntity() {
        HttpStatus status = HttpStatus.resolve(this.code);
        return ResponseEntity.status(status != null ? status : HttpStatus.OK).body(this);
    }
}