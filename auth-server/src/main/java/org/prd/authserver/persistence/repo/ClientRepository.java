package org.prd.authserver.persistence.repo;

import org.prd.authserver.persistence.entity.ClientApp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.oauth2.server.authorization.settings.ConfigurationSettingNames;

import java.util.Optional;

public interface ClientRepository extends MongoRepository<ClientApp, String> {
    Optional<ClientApp> findByClientId(String clientId);
}