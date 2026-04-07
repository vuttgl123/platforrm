package com.meta_forge_platform.shared.domain.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object[] args;
    private final Object meta;

    public AppException(ErrorCode errorCode, Object[] args, Object meta) {
        super(errorCode.name());
        this.errorCode = errorCode;
        this.args = args;
        this.meta = meta;
    }

    public static AppException of(ErrorCode code, Object... args) {
        return new AppException(code, args, null);
    }

    public static AppException withMeta(ErrorCode code, Object meta, Object... args) {
        return new AppException(code, args, meta);
    }
}