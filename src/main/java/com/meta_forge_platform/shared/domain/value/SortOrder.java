package com.meta_forge_platform.shared.domain.value;

public record SortOrder(Integer value) {

    public SortOrder {
        if (value == null) {
            value = 0;
        }
        if (value < 0) {
            throw new IllegalArgumentException("Sort order must be >= 0");
        }
    }

    public static SortOrder zero() {
        return new SortOrder(0);
    }
}
