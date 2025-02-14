package org.prd.authserver.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "client_app")
public class ClientApp {

    @MongoId
    private String id;
    private String clientId;
    private String clientSecret;
    private String clientName;
    private List<String> authenticationMethods;
    private List<String> authorizationGrantTypes;
    private List<String> redirectUris;
    private List<String> scopes;
    private int durationInMinutes;
    private boolean requireProofKey;



    public static RegisteredClient toRegisteredClient(ClientApp clientApp) {
        return RegisteredClient.withId(clientApp.getClientId())
                .clientId(clientApp.getClientId())
                .clientSecret(clientApp.getClientSecret())
                .clientName(clientApp.getClientName())
                .authorizationGrantTypes(gts->{
                    gts.addAll(clientApp.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::new).toList());
                })
                .clientAuthenticationMethods(cams->{
                    cams.addAll(clientApp.getAuthenticationMethods().stream().map(ClientAuthenticationMethod::new).toList());
                })
                .redirectUris(rus ->{
                    rus.addAll(clientApp.getRedirectUris());
                })
                //.postLogoutRedirectUris(clientApp.getPostLogoutRedirectUris())
                .scopes(scopes->{
                    scopes.addAll(clientApp.getScopes());
                })
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(clientApp.isRequireProofKey())
                        .build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(clientApp.getDurationInMinutes()))
                        .refreshTokenTimeToLive(Duration.ofMinutes(clientApp.getDurationInMinutes() * 4L))
                        .build())
                .build();
    }
}