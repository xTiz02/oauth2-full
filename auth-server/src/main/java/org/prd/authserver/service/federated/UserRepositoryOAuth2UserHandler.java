package org.prd.authserver.service.federated;

import lombok.extern.slf4j.Slf4j;
import org.prd.authserver.persistence.entity.GoogleUser;
import org.prd.authserver.persistence.repo.GoogleUserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Service
@Slf4j
public final class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {


    private final GoogleUserRepository googleUserRepository;

    public  UserRepositoryOAuth2UserHandler(GoogleUserRepository googleUserRepository) {
        this.googleUserRepository = googleUserRepository;
    }

    @Override
    public void accept(OAuth2User user) {
        // Captura el usuario de google y lo guarda en la base de datos
        log.info("Usuario de un proveedor: ");
        user.getAttributes().forEach((k,v)-> System.out.println(k + " : " + v));
        if (this.googleUserRepository.findByEmail(user.getName()).isEmpty()) {
            GoogleUser googleUser = GoogleUser.fromOauth2User(user);
            log.info(googleUser.toString());
            this.googleUserRepository.save(googleUser);
        } else {
            log.info("Usuario reconocido {}", user.getAttributes().get("given_name"));
        }
    }

}