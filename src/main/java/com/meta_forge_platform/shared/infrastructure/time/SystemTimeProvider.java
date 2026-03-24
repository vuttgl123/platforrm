package com.meta_forge_platform.shared.infrastructure.time;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class SystemTimeProvider implements TimeProvider {

    @Override
    public LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }

    @Override
    public LocalDate nowDate() {
        return LocalDate.now();
    }
}
