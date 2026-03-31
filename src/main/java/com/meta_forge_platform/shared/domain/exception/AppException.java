package com.meta_forge_platform.shared.domain.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }

    public AppException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AppException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public static AppException notFound(String entityName, Object identifier) {
        return new AppException(ErrorCode.NOT_FOUND, String.format("%s không tồn tại: %s", entityName, identifier));
    }

    public static AppException notFound(ErrorCode code, String entityName, Object identifier) {
        return new AppException(code, String.format("%s không tồn tại: %s", entityName, identifier));
    }

    public static AppException conflict(String message) {
        return new AppException(ErrorCode.CONFLICT, message);
    }

    public static AppException badRequest(String message) {
        return new AppException(ErrorCode.BAD_REQUEST, message);
    }

    public static AppException forbidden(String message) {
        return new AppException(ErrorCode.FORBIDDEN, message);
    }

    public static AppException optimisticLock() {
        return new AppException(ErrorCode.OPTIMISTIC_LOCK);
    }

    public static AppException alreadyDeleted(String entityName, Object identifier) {
        return new AppException(ErrorCode.RECORD_ALREADY_DELETED, String.format("%s đã bị xóa: %s", entityName, identifier));
    }
}