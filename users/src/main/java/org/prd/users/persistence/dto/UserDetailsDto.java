package org.prd.users.persistence.dto;

import org.prd.users.util.RoleEnum;

import java.util.List;

public record UserDetailsDto(
        String username,
        String password,
        String email,
        boolean account_expired,
        boolean account_locked,
        boolean credentials_expired,
        boolean enabled,
        RoleEnum role,
        List<String> permissions

) {
}