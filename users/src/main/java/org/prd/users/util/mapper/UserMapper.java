package org.prd.users.util.mapper;

import org.prd.users.persistence.dto.UserDetailsDto;
import org.prd.users.persistence.entity.User;

public class UserMapper {

    public static UserDetailsDto toUserDetailsDto(User user){
        return new UserDetailsDto(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.isAccount_expired(),
                user.isAccount_locked(),
                user.isCredentials_expired(),
                user.isEnabled(),
                user.getRole().getName(),
                user.getPermissionsArray()
        );
    }
}