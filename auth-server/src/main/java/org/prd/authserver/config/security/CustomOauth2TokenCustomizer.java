package org.prd.authserver.config.security;

import lombok.extern.slf4j.Slf4j;
import org.prd.authserver.service.security.OidcUserInfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CustomOauth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final OidcUserInfoService oidcUserInfoService;

    public CustomOauth2TokenCustomizer(OidcUserInfoService oidcUserInfoService) {
        this.oidcUserInfoService = oidcUserInfoService;
    }

    @Override
    public void customize(JwtEncodingContext context) {
        log.info("Generando token...");
        Authentication authentication = context.getPrincipal();
        /*Access Token Default Claim: {sub=admin1, aud=[client],
            nbf=2025-02-16T15:24:22.778757400Z,
            scope=[address, phone, openid, profile, email],
            iss=http://localhost:9595/authorization-server,
            exp=2025-02-16T15:34:22.778757400Z,
            iat=2025-02-16T15:24:22.778757400Z,
            jti=cfc8d858-0c8c-4207-b05d-812a001229a0}*/

        /*Id token Default Claim: {sub=admin1, aud=[client],
            azp=client,
            auth_time=Sun Feb 16 10:24:22 PET 2025,
            iss=http://localhost:9595/authorization-server,
            exp=2025-02-16T15:54:22.898788500Z,
            iat=2025-02-16T15:24:22.898788500Z,
            jti=dff98d87-1aa7-4aec-85c8-650cdad22762, sid=rct2IoxwhtosEfdFGiAdBX-HZy-eYipmEZkzoE5lq7c}*/
        if(OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())){
            List<String> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            context.getClaims().claim("permissions", authorities);
        }
        //google OAuth2AuthenticationToken -> OauthUser
        //login UsernamePasswordAuthenticationToken -> UserDetails
        Map<String, Object> claims;
        if(OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())){
            if(authentication instanceof OAuth2AuthenticationToken auth){
                OAuth2User oauth2User = auth.getPrincipal();
                claims = new HashMap<>(oauth2User.getAttributes());
                log.info("OAuth2AuthenticationToken class: {}", claims);
                context.getClaims().claims(existingClaims -> {
                    existingClaims.keySet().forEach(claims::remove);
                    ID_TOKEN_CLAIMS.forEach(claims::remove);
                    existingClaims.putAll(claims);
                });
                return;
            }
            if (authentication instanceof UsernamePasswordAuthenticationToken auth) {
                String username = auth.getName();
                claims = oidcUserInfoService.getUserInfo(username).getClaims();
                log.info("UsernamePasswordAuthenticationToken class: {}", claims.toString());
                context.getClaims().claims(claim -> {
                    claim.putAll(claims);
                });
            }
        }

    }


    private static final Set<String> ID_TOKEN_CLAIMS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            IdTokenClaimNames.ISS,
            IdTokenClaimNames.SUB,
            IdTokenClaimNames.AUD,
            IdTokenClaimNames.EXP,
            IdTokenClaimNames.IAT,
            IdTokenClaimNames.AUTH_TIME,
            IdTokenClaimNames.NONCE,
            IdTokenClaimNames.ACR,
            IdTokenClaimNames.AMR,
            IdTokenClaimNames.AZP,
            IdTokenClaimNames.AT_HASH,
            IdTokenClaimNames.C_HASH
    )));
}