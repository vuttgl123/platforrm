package com.meta_forge_platform.shared.application;

import com.meta_forge_platform.shared.domain.query.PageQuery;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, C, U, ID> {

    Page<T> findAll(PageQuery query);

    List<T> findAll();

    Optional<T> findById(ID id);

    T getById(ID id);

    T create(C command);

    T update(ID id, U command);

    void deleteById(ID id);

    void deleteByIds(List<ID> ids);

    boolean existsById(ID id);
}