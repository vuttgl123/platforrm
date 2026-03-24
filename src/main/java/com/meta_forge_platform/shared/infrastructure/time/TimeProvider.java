package com.meta_forge_platform.shared.infrastructure.time;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TimeProvider {
    LocalDateTime nowDateTime();
    LocalDate nowDate();
}