package com.meta_forge_platform.shared.domain.value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public record JsonMap(Map<String, Object> value) {

    public JsonMap {
        value = value == null ? new HashMap<>() : new HashMap<>(value);
    }

    public static JsonMap empty() {
        return new JsonMap(Map.of());
    }

    public Object get(String key) {
        return value.get(key);
    }

    public String getString(String key) {
        Object raw = value.get(key);
        return raw == null ? null : String.valueOf(raw);
    }

    public Integer getInt(String key) {
        Object raw = value.get(key);
        if (raw == null) {
            return null;
        }
        if (raw instanceof Number number) {
            return number.intValue();
        }
        return Integer.parseInt(String.valueOf(raw));
    }

    public Boolean getBoolean(String key) {
        Object raw = value.get(key);
        if (raw == null) {
            return null;
        }
        if (raw instanceof Boolean bool) {
            return bool;
        }
        return Boolean.parseBoolean(String.valueOf(raw));
    }

    public Map<String, Object> toMap() {
        return Collections.unmodifiableMap(value);
    }
}