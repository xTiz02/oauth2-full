package org.prd.authserver.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//@Slf4j
//@Component
public class CustomOauth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    @Override
    public void customize(JwtEncodingContext context) {
        //log.info("Generando nuevo token con permisos del usuario...");
        Authentication authentication = context.getPrincipal();

        String tokenType = context.getTokenType().getValue();
        if(tokenType.equals("access_token")){

            List<String> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            context.getClaims().claim("permissions", authorities);
        }

        //Tambien se puede customizar el token_id para añadir foto del usuario su correo etc. De nuestra app.
    }
}