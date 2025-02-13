package org.prd.users.services;

import org.prd.users.persistence.dto.UserDetailsDto;

public interface UserService {

    UserDetailsDto findByUsername(String username);
}