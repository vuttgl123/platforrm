package com.meta_forge_platform.shared.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SUCCESS(HttpStatus.OK, "SUCCESS", "Thành công"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Yêu cầu không hợp lệ"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Chưa xác thực"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "Không có quyền truy cập"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "Không tìm thấy dữ liệu"),
    CONFLICT(HttpStatus.CONFLICT, "CONFLICT", "Dữ liệu đã tồn tại"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Lỗi hệ thống"),
    VALIDATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY, "VALIDATION_ERROR", "Dữ liệu không hợp lệ"),

    OPTIMISTIC_LOCK(HttpStatus.CONFLICT, "OPTIMISTIC_LOCK", "Dữ liệu đã bị thay đổi bởi người dùng khác, vui lòng tải lại"),

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "ENTITY_NOT_FOUND", "Không tìm thấy entity"),
    RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "RECORD_NOT_FOUND", "Không tìm thấy record"),
    RECORD_ALREADY_DELETED(HttpStatus.GONE, "RECORD_ALREADY_DELETED", "Record đã bị xóa"),
    RECORD_DUPLICATE(HttpStatus.CONFLICT, "RECORD_DUPLICATE", "Record đã tồn tại"),

    MODULE_NOT_FOUND(HttpStatus.NOT_FOUND, "MODULE_NOT_FOUND", "Không tìm thấy module"),
    FIELD_NOT_FOUND(HttpStatus.NOT_FOUND, "FIELD_NOT_FOUND", "Không tìm thấy field"),
    FIELD_REQUIRED(HttpStatus.BAD_REQUEST, "FIELD_REQUIRED", "Field bắt buộc không được để trống"),


    WORKFLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "WORKFLOW_NOT_FOUND", "Không tìm thấy workflow"),
    TRANSITION_INVALID(HttpStatus.BAD_REQUEST, "TRANSITION_INVALID", "Chuyển trạng thái không hợp lệ"),

    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "FILE_NOT_FOUND", "Không tìm thấy file"),
    FILE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "FILE_TOO_LARGE", "File vượt quá kích thước cho phép"),
    FILE_TYPE_NOT_ALLOWED(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "FILE_TYPE_NOT_ALLOWED", "Loại file không được hỗ trợ");

    private final HttpStatus httpStatus;
    private final String code;
    private final String defaultMessage;
}