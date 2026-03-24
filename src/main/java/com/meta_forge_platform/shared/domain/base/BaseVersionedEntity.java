package com.meta_forge_platform.shared.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseVersionedEntity extends BaseEntity {

    @Column(name = "version_no", nullable = false)
    protected Long versionNo = 1L;

    public void increaseVersion() {
        this.versionNo = this.versionNo == null ? 1L : this.versionNo + 1;
    }
}
