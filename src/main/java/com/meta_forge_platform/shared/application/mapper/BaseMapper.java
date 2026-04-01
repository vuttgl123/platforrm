package com.meta_forge_platform.shared.application.mapper;

import com.meta_forge_platform.shared.application.dto.BaseDto;
import com.meta_forge_platform.shared.domain.base.BaseEntity;
import java.util.List;

public interface BaseMapper<E extends BaseEntity, D extends BaseDto, S, C, U> {
    D toDto(E entity);
    S toSummaryDto(E entity);
    E toEntity(C command);
    void updateEntity(E entity, U command);
    List<D> toDtoList(List<E> entities);
    List<S> toSummaryDtoList(List<E> entities);
}