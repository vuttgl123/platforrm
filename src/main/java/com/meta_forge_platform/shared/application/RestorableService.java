package com.meta_forge_platform.shared.application;

public interface RestorableService<T, ID> {

    T restore(ID id);
}