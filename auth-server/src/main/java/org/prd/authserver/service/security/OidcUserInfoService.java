package org.prd.authserver.service.security;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.prd.authserver.persistence.dto.ApiResponse;
import org.prd.authserver.persistence.dto.UserDetailsDto;
import org.prd.authserver.service.feign.UserFeignService;
import org.prd.authserver.util.Util;
import org.prd.authserver.web.exception.ObjectNotFoundException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class OidcUserInfoService {

    private final UserFeignService userFeignService;

    public OidcUserInfoService(UserFeignService userFeignService) {
        this.userFeignService = userFeignService;
    }

    public OidcUserInfo getUserInfo(String username) {

        UserDetailsDto userDetailsDto = null;
        try{
            userDetailsDto = userFeignService.getUserByUsername(username);
        }catch (FeignException e){
            if(e.responseBody().isPresent()){
                ApiResponse apiResponse = Util.getClassFromBytes(e.responseBody().get().array(), ApiResponse.class);
                throw new ObjectNotFoundException(apiResponse.message());
            }
        }catch (Exception e){
            throw new ObjectNotFoundException("Error inesperado al obtener el usurario [" + username+ "]");
        }
        assert userDetailsDto != null;
        //Verificar si los datos del usuario son null y asignar valores por defecto ""
        log.info("Información de usuario: {}", userDetailsDto);
        return OidcUserInfo.builder()
                .subject(username)
                //.name("First Last")
                //.givenName("First")
                //.familyName("Last")
                //.middleName("Middle")
                .nickname(username)
                //.preferredUsername(username)
                .profile("https://w7.pngwing.com/pngs/178/595/png-transparent-user-profile-computer-icons-login-user-avatars-thumbnail.png")
                //.picture("https://w7.pngwing.com/pngs/178/595/png-transparent-user-profile-computer-icons-login-user-avatars-thumbnail.png")
                //.website("https://example.com")
                .email(userDetailsDto.email()!=null ? userDetailsDto.email() : "")
                .emailVerified(true)
                .gender("male")
                .birthdate("2002-10-26")
                .zoneinfo("America/Perú")
                //.locale("en-US")
                .phoneNumber("+51 923-145-123;ext=5678")
                .phoneNumberVerified(false)
                .claim("address", Collections.singletonMap("formatted", "Champ de Mars\n5 Av. Anatole France\n75007 Paris\nFrance"))
                .updatedAt("2021-10-26T21:32:52Z")
                .build();
    }
}