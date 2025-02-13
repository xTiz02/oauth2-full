package org.prd.users.services.impl;


import org.prd.users.persistence.dto.UserDetailsDto;
import org.prd.users.persistence.repo.UserRepository;
import org.prd.users.services.UserService;
import org.prd.users.util.mapper.UserMapper;
import org.prd.users.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::toUserDetailsDto)
                .orElseThrow(() -> new ObjectNotFoundException("No se encontró el usuario con nombre [" + username+ "]"));
    }
}