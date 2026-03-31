package com.meta_forge_platform.shared.application;

import com.meta_forge_platform.shared.domain.query.PageQuery;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Interface CRUD generic cho tất cả Service.
 *
 * @param <T>   DTO hoặc Domain object trả về
 * @param <C>   Command/DTO tạo mới (Create)
 * @param <U>   Command/DTO cập nhật (Update)
 * @param <ID>  Kiểu dữ liệu khóa chính
 */
public interface BaseService<T, C, U, ID> {

    /** Lấy danh sách có phân trang + filter + sort */
    Page<T> findAll(PageQuery query);

    /** Lấy tất cả không phân trang (dùng cho dropdown, export) */
    List<T> findAll();

    /** Tìm theo id, trả về Optional */
    Optional<T> findById(ID id);

    /** Tìm theo id, ném AppException nếu không tìm thấy */
    T getById(ID id);

    /** Tạo mới */
    T create(C command);

    /** Cập nhật theo id */
    T update(ID id, U command);

    /** Xóa theo id (soft delete nếu entity hỗ trợ, hard delete nếu không) */
    void deleteById(ID id);

    /** Xóa nhiều theo danh sách id */
    void deleteByIds(List<ID> ids);

    /** Kiểm tra tồn tại */
    boolean existsById(ID id);
}