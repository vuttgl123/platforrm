package com.meta_forge_platform.shared.infrastructure.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TraceIdFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(TraceIdFilter.class);

    public static final String TRACE_ID_HEADER = "X-Request-Id";
    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long startTime = System.currentTimeMillis();

        String traceId = request.getHeader(TRACE_ID_HEADER);
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString();
        }

        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        MDC.put("traceId", traceId);
        MDC.put("correlationId", correlationId);

        response.setHeader(TRACE_ID_HEADER, traceId);
        response.setHeader(CORRELATION_ID_HEADER, correlationId);

        log.info("→ {} {} from {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr());

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("← {} {} - Status: {} - Duration: {}ms", request.getMethod(), request.getRequestURI(), response.getStatus(), duration);
            MDC.remove("traceId");
            MDC.remove("correlationId");
        }
    }
}