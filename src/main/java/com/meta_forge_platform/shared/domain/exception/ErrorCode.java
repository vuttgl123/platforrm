package com.meta_forge_platform.shared.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SUCCESS(HttpStatus.OK, "success"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "error.bad_request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "error.unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "error.forbidden"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "error.not_found"),
    CONFLICT(HttpStatus.CONFLICT, "error.conflict"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "error.internal"),
    VALIDATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY, "error.validation"),

    OPTIMISTIC_LOCK(HttpStatus.CONFLICT, "error.optimistic_lock"),

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "entity.not_found"),
    RECORD_ALREADY_DELETED(HttpStatus.GONE, "record.deleted"),
    RECORD_DUPLICATE(HttpStatus.CONFLICT, "record.duplicate"),

    FIELD_REQUIRED(HttpStatus.BAD_REQUEST, "field.required"),

    TRANSITION_INVALID(HttpStatus.BAD_REQUEST, "workflow.transition_invalid"),

    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "file.not_found"),
    FILE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "file.too_large"),
    FILE_TYPE_NOT_ALLOWED(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "file.type_not_allowed");

    private final HttpStatus httpStatus;
    private final String messageKey;
}