package com.meta_forge_platform.shared.domain.exception;

import lombok.Getter;

@Getter
public class ValidationException extends DomainException {

    private final String field;

    public ValidationException(String field, String code, Object... args) {
        super(code, args);
        this.field = field;
    }

}
