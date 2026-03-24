package com.meta_forge_platform.shared.infrastructure.logging;

import org.slf4j.MDC;

import java.util.UUID;

public class CorrelationContext {

    private static final String CORRELATION_ID_KEY = "correlationId";

    public static UUID getCorrelationId() {
        String id = MDC.get(CORRELATION_ID_KEY);
        return id != null ? UUID.fromString(id) : null;
    }

    public static String getCorrelationIdAsString() {
        return MDC.get(CORRELATION_ID_KEY);
    }

    public static void setCorrelationId(UUID correlationId) {
        MDC.put(CORRELATION_ID_KEY, correlationId.toString());
    }

    public static void setCorrelationId(String correlationId) {
        MDC.put(CORRELATION_ID_KEY, correlationId);
    }
}