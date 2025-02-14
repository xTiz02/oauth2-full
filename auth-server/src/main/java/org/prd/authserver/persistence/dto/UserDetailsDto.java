package org.prd.authserver.persistence.dto;

import java.util.List;

public record UserDetailsDto(
        String username,
        String password,
        String email,
        boolean account_expired,
        boolean account_locked,
        boolean credentials_expired,
        boolean enabled,
        String role,
        List<String> permissions
) {
}