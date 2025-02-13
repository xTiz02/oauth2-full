package org.prd.users.persistence.dto;

import java.time.LocalDateTime;

public record ApiResponse(
        String message,
        LocalDateTime timestamp,
        boolean success
) {
}