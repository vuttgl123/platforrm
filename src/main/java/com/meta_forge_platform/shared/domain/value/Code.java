package com.meta_forge_platform.shared.domain.value;

import com.meta_forge_platform.shared.domain.constant.RegexConstants;

public record Code(String value) {

    public Code {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Code must not be blank");
        }

        value = value.trim();

        if (!RegexConstants.CODE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid code format: " + value);
        }
    }

    public static Code of(String value) {
        return new Code(value);
    }
}
