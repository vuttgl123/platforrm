package com.meta_forge_platform.shared.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseActivableEntity extends BaseEntity {

    @Column(name = "is_active", nullable = false)
    protected Boolean active = Boolean.TRUE;

    public void activate() {
        this.active = Boolean.TRUE;
    }

    public void deactivate() {
        this.active = Boolean.FALSE;
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(active);
    }
}
