package com.meta_forge_platform.shared.domain.exception;

import lombok.Getter;

@Getter
public abstract class DomainException extends RuntimeException {

    private final String code;
    private final Object[] args;

    protected DomainException(String code, Object... args) {
        super(code);
        this.code = code;
        this.args = args;
    }
}