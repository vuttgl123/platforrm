//package com.meta_forge_platform.shared.interfaces;
//
//import com.meta_forge_platform.shared.application.BaseService;
//import com.meta_forge_platform.shared.domain.query.FilterQuery;
//import com.meta_forge_platform.shared.domain.query.PageQuery;
//import com.meta_forge_platform.shared.domain.query.SortQuery;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//public abstract class BaseRestController<T, C, U, ID> {
//
//    protected final BaseService<T, C, U, ID> service;
//
//    @GetMapping
//    @Operation(summary = "Lấy danh sách có phân trang, filter, sort")
//    public ResponseEntity<ApiResponse<List<T>>> findAll(
//            @Parameter(description = "Trang hiện tại (bắt đầu từ 0)")
//            @RequestParam(defaultValue = "0") int page,
//
//            @Parameter(description = "Số bản ghi mỗi trang")
//            @RequestParam(defaultValue = "20") int size,
//
//            @Parameter(description = "Sort: field,asc|desc. Ví dụ: createdAt,desc")
//            @RequestParam(required = false) List<String> sort,
//
//            @Parameter(description = "Filter: field:OPERATOR:value. Ví dụ: status:EQ:ACTIVE")
//            @RequestParam(required = false) List<String> filter,
//
//            @Parameter(description = "Tìm kiếm toàn văn")
//            @RequestParam(required = false) String keyword
//    ) {
//        PageQuery query = buildPageQuery(page, size, sort, filter, keyword);
//        Page<T> result = service.findAll(query);
//
//        return ResponseEntity.ok(
//                ApiResponse.success(result.getContent(), PageMeta.from(result))
//        );
//    }
//
//    @GetMapping("/{id}")
//    @Operation(summary = "Lấy chi tiết theo id")
//    public ResponseEntity<ApiResponse<T>> getById(@PathVariable ID id) {
//        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
//    }
//
//    @PostMapping
//    @Operation(summary = "Tạo mới")
//    public ResponseEntity<ApiResponse<T>> create(@Valid @RequestBody C command) {
//        T created = service.create(command);
//        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(created));
//    }
//
//    @PutMapping("/{id}")
//    @Operation(summary = "Cập nhật toàn bộ theo id")
//    public ResponseEntity<ApiResponse<T>> update(
//            @PathVariable ID id,
//            @Valid @RequestBody U command
//    ) {
//        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", service.update(id, command)));
//    }
//
//    @DeleteMapping("/{id}")
//    @Operation(summary = "Xóa theo id")
//    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable ID id) {
//        service.deleteById(id);
//        return ResponseEntity.ok(ApiResponse.success("Xóa thành công", null));
//    }
//
//    @DeleteMapping("/batch")
//    @Operation(summary = "Xóa nhiều bản ghi")
//    public ResponseEntity<ApiResponse<Void>> deleteBatch(@RequestBody List<ID> ids) {
//        service.deleteByIds(ids);
//        return ResponseEntity.ok(ApiResponse.success("Xóa " + ids.size() + " bản ghi thành công", null));
//    }
//
//    @GetMapping("/all")
//    @Operation(summary = "Lấy tất cả không phân trang (dùng cho select/dropdown)")
//    public ResponseEntity<ApiResponse<List<T>>> findAllNoPaging() {
//        return ResponseEntity.ok(ApiResponse.success(service.findAll()));
//    }
//
//    protected PageQuery buildPageQuery(int page, int size,
//                                       List<String> sort, List<String> filter,
//                                       String keyword) {
//        List<SortQuery> sorts = sort == null ? List.of()
//                : sort.stream().map(SortQuery::parse).toList();
//
//        List<FilterQuery> filters = filter == null ? List.of()
//                : filter.stream().map(FilterQuery::parse).toList();
//
//        return PageQuery.builder()
//                .page(page)
//                .size(size)
//                .sorts(sorts)
//                .filters(filters)
//                .keyword(keyword)
//                .build();
//    }
//}