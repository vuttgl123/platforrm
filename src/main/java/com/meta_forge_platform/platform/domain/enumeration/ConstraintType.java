package com.meta_forge_platform.platform.domain.enumeration;

public enum ConstraintType {

    REQUIRED,
    UNIQUE,
    MIN,
    MAX,
    LENGTH,
    REGEX,

    EXPRESSION,
    SCRIPT,

    CROSS_FIELD,

    CONDITION
}