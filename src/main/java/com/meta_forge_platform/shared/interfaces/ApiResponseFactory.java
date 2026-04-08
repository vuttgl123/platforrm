package com.meta_forge_platform.shared.interfaces;

import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import com.meta_forge_platform.shared.interfaces.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiResponseFactory {

    private final MessageUtil messageUtil;

    public <T> ApiResponse<T> success(T data) {
        return build(ErrorCode.SUCCESS, data, null);
    }

    public <T> ApiResponse<T> success(T data, PageMeta meta) {
        return build(ErrorCode.SUCCESS, data, meta);
    }

    public ApiResponse<Void> success() {
        return build(ErrorCode.SUCCESS, null, null);
    }

    // ── Error (không có args) ─────────────────────────────────────────────
    // Dùng cho: BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, NOT_FOUND,
    //           INTERNAL_ERROR, VALIDATION_ERROR, OPTIMISTIC_LOCK,
    //           TRANSITION_INVALID, FILE_NOT_FOUND, FILE_TOO_LARGE, FILE_TYPE_NOT_ALLOWED

    public <T> ApiResponse<T> error(ErrorCode errorCode) {
        return build(errorCode, null, null);
    }

    // ── Error (có args) ───────────────────────────────────────────────────
    // Dùng cho: ENTITY_NOT_FOUND     → args: entityName, id
    //           RECORD_ALREADY_DELETED → args: entityName, id
    //           RECORD_DUPLICATE     → args: field, value
    //           FIELD_REQUIRED       → args: fieldName

    public <T> ApiResponse<T> error(ErrorCode errorCode, Object... args) {
        return build(errorCode, null, null, args);
    }

    private <T> ApiResponse<T> build(ErrorCode errorCode, T data, PageMeta meta, Object... args) {
        String message = messageUtil.getMessage(errorCode.getMessageKey(), args);
        return ApiResponse.of(errorCode, message, data, meta);
    }
}
