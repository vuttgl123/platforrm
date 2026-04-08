package com.meta_forge_platform.platform.domain.entity;

import com.meta_forge_platform.platform.domain.enumeration.ModuleStatus;
import com.meta_forge_platform.shared.domain.base.SoftDeletableEntity;
import com.meta_forge_platform.shared.domain.exception.AppException;
import com.meta_forge_platform.shared.domain.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "dp_module",
        uniqueConstraints = @UniqueConstraint(name = "uk_dp_module_code", columnNames = "module_code"))
public class DpModule extends SoftDeletableEntity {

    @Column(name = "module_code", nullable = false, length = 100)
    private String moduleCode;

    @Column(name = "module_name", nullable = false, length = 255)
    private String moduleName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private ModuleStatus status;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "is_system", nullable = false)
    private Boolean isSystem;

    @Version
    @Column(name = "version_no", nullable = false)
    private Long versionNo;

    public static DpModule create(String code, String name) {
        DpModule m = new DpModule();
        m.moduleCode = code;
        m.moduleName = name;
        m.status = ModuleStatus.ACTIVE;
        m.sortOrder = 0;
        m.isSystem = false;
        return m;
    }

    public void applyMetadata(
            String moduleName,
            String description,
            ModuleStatus status,
            Integer sortOrder,
            Boolean isSystem
    ) {
        this.moduleName = moduleName;
        this.description = description;
        this.status = status;
        this.sortOrder = sortOrder;
        this.isSystem = isSystem;
    }

    public void delete(String deletedBy) {
        if (Boolean.TRUE.equals(this.isSystem)) {
            throw AppException.of(ErrorCode.FORBIDDEN, "SYSTEM_MODULE");
        }
        if (isDeleted()) {
            throw AppException.of(ErrorCode.RECORD_ALREADY_DELETED, "DpModule", getId());
        }
        softDelete(deletedBy);
    }
}