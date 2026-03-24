package com.meta_forge_platform.shared.domain.constant;

import java.util.regex.Pattern;

public final class RegexConstants {

    private RegexConstants() {
    }

    public static final Pattern CODE_PATTERN =
            Pattern.compile("^[a-zA-Z][a-zA-Z0-9_\\-.]{1,99}$");
}
