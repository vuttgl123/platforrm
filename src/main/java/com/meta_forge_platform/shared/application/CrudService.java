package com.meta_forge_platform.shared.application;

public interface CrudService<T, C, U, ID>
        extends BaseService<T, C, U, ID>, RestorableService<T, ID> {
}
