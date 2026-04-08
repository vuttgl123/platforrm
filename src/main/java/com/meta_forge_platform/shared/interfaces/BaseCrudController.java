package com.meta_forge_platform.shared.interfaces;

import com.meta_forge_platform.shared.application.CrudService;
import com.meta_forge_platform.shared.domain.query.PageQuery;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseCrudController<T, C, U, ID> {

    protected final CrudService<T, C, U, ID> service;
    protected final ApiResponseFactory responseFactory;

    protected BaseCrudController(CrudService<T, C, U, ID> service,
                                 ApiResponseFactory responseFactory) {
        this.service = service;
        this.responseFactory = responseFactory;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<T>>> findAll(@ModelAttribute PageQuery query) {
        Page<T> page = service.findAll(query);
        return responseFactory
                .success(page.getContent(), PageMeta.from(page))
                .toResponseEntity();
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<T>>> findAll() {
        return responseFactory
                .success(service.findAll())
                .toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<T>> getById(@PathVariable ID id) {
        return responseFactory
                .success(service.getById(id))
                .toResponseEntity();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<T>> create(@Valid @RequestBody C command) {
        return responseFactory
                .success(service.create(command))
                .toResponseEntity();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<T>> update(@PathVariable ID id,
                                                 @Valid @RequestBody U command) {
        return responseFactory
                .success(service.update(id, command))
                .toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable ID id) {
        service.deleteById(id);
        return responseFactory
                .<Void>success(null)
                .toResponseEntity();
    }

    @PostMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<T>> restore(@PathVariable ID id) {
        return responseFactory
                .success(service.restore(id))
                .toResponseEntity();
    }
}