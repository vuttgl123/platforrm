package com.meta_forge_platform.shared.domain.exception;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageResolver messageResolver;

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiErrorResponse> handleAppException(AppException ex, Locale locale) {
        String message = messageResolver.resolve(
                ex.getErrorCode(),
                ex.getArgs(),
                locale
        );

        log.warn("AppException [{}]: {}", ex.getErrorCode().name(), message);

        return ResponseEntity.status(ex.getErrorCode().getHttpStatus())
                .body(ApiErrorResponse.of(
                        ex.getErrorCode().name(),
                        message,
                        ex.getMeta()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            Locale locale
    ) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            errors.put(err.getField(), err.getDefaultMessage());
        }

        String message = messageResolver.resolve(
                ErrorCode.VALIDATION_ERROR,
                null,
                locale
        );

        return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getHttpStatus())
                .body(ApiErrorResponse.of(
                        ErrorCode.VALIDATION_ERROR.name(),
                        message,
                        errors
                ));
    }

    @ExceptionHandler({
            OptimisticLockException.class,
            ObjectOptimisticLockingFailureException.class
    })
    public ResponseEntity<ApiErrorResponse> handleOptimisticLock(Exception ex, Locale locale) {
        String message = messageResolver.resolve(
                ErrorCode.OPTIMISTIC_LOCK,
                null,
                locale
        );

        log.warn("Optimistic lock conflict", ex);

        return ResponseEntity.status(ErrorCode.OPTIMISTIC_LOCK.getHttpStatus())
                .body(ApiErrorResponse.of(
                        ErrorCode.OPTIMISTIC_LOCK.name(),
                        message,
                        null
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrity(
            DataIntegrityViolationException ex,
            Locale locale
    ) {
        String message = messageResolver.resolve(
                ErrorCode.CONFLICT,
                null,
                locale
        );

        log.warn("Data integrity violation", ex);

        return ResponseEntity.status(ErrorCode.CONFLICT.getHttpStatus())
                .body(ApiErrorResponse.of(
                        ErrorCode.CONFLICT.name(),
                        message,
                        null
                ));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            NoHandlerFoundException ex,
            Locale locale
    ) {
        String message = messageResolver.resolve(
                ErrorCode.NOT_FOUND,
                null,
                locale
        );

        return ResponseEntity.status(ErrorCode.NOT_FOUND.getHttpStatus())
                .body(ApiErrorResponse.of(
                        ErrorCode.NOT_FOUND.name(),
                        message,
                        ex.getRequestURL()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneral(Exception ex, Locale locale) {
        String message = messageResolver.resolve(
                ErrorCode.INTERNAL_ERROR,
                null,
                locale
        );

        log.error("Unhandled exception", ex);

        return ResponseEntity.status(ErrorCode.INTERNAL_ERROR.getHttpStatus())
                .body(ApiErrorResponse.of(
                        ErrorCode.INTERNAL_ERROR.name(),
                        message,
                        null
                ));
    }
}