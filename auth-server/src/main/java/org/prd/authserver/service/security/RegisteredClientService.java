package org.prd.authserver.service.security;

import lombok.extern.slf4j.Slf4j;
import org.prd.authserver.persistence.entity.ClientApp;
import org.prd.authserver.persistence.repo.ClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegisteredClientService implements RegisteredClientRepository {

    private final ClientRepository clientRepository;

    public RegisteredClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        log.info("Buscando cliente con id: {}", id);
        RegisteredClient registeredClient = ClientApp.toRegisteredClient(clientRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Client not found by id")));

        log.info("Cliente encontrado con id: {}", registeredClient);
        return registeredClient;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        log.info("Buscando cliente con clientId: {}", clientId);
        ClientApp clientApp = clientRepository.findByClientId(clientId)
                .orElseThrow(()->new RuntimeException("Client not found"));

        RegisteredClient registeredClient = ClientApp.toRegisteredClient(clientApp);
        log.info("Cliente encontrado con clientId: {}", registeredClient);
        return registeredClient;
    }
}