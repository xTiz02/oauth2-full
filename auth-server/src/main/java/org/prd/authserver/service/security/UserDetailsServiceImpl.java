package org.prd.authserver.service.security;

import feign.FeignException;
import org.prd.authserver.persistence.dto.ApiResponse;
import org.prd.authserver.persistence.dto.UserDetailsDto;
import org.prd.authserver.service.feign.UserFeignService;
import org.prd.authserver.util.Util;
import org.prd.authserver.web.exception.ObjectNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserFeignService userFeignService;

    public UserDetailsServiceImpl(UserFeignService userFeignService) {
        this.userFeignService = userFeignService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsDto userDetailsDto = null;
        try{
            userDetailsDto = userFeignService.getUserByUsername(username);
        }catch (FeignException e){
            if(e.responseBody().isPresent()){
                ApiResponse apiResponse = Util.getClassFromBytes(e.responseBody().get().array(), ApiResponse.class);
                throw new ObjectNotFoundException(apiResponse.message());
            }
        }
        assert userDetailsDto != null;
        List<GrantedAuthority> authorities = new ArrayList<>();
        userDetailsDto.permissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
        authorities.add(new SimpleGrantedAuthority(userDetailsDto.role()));


        return new User(
                userDetailsDto.username(),
                userDetailsDto.password(),
                userDetailsDto.enabled(),
                !userDetailsDto.account_expired(),
                !userDetailsDto.credentials_expired(),
                !userDetailsDto.account_locked(),
                authorities );
    }
}