package com.meta_forge_platform.shared.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseSortableEntity extends BaseEntity {

    @Column(name = "sort_order", nullable = false)
    protected Integer sortOrder = 0;
}
