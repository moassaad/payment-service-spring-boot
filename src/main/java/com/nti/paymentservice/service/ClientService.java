package com.nti.paymentservice.service;

import com.nti.paymentservice.dto.ClientAuthDto;
import com.nti.paymentservice.entity.ClientEntity;
import com.nti.paymentservice.exception.ClientAlreadyExistsException;
import com.nti.paymentservice.exception.ClientNotFoundException;
import com.nti.paymentservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientAuthDto register(String username, String password) throws Exception {

        Optional<ClientEntity> clientEntity = clientRepository.findByUsernameAndPassword(username, password);

        if (clientEntity.isPresent()) {
            log.warn("username username={} already exists ", username);
            throw new ClientAlreadyExistsException("username already exists");
        }

        ClientEntity newClient = new ClientEntity();

        newClient.setUsername(username);
        newClient.setPassword(password);
        newClient.setApiKey(UUID.randomUUID());

        log.info("set new client app client={} ", newClient.getUsername());

        clientRepository.save(newClient);

        ClientAuthDto clientAuthDto = new ClientAuthDto();
        clientAuthDto.setApiKey(newClient.getApiKey());

        log.info("add client successfuly client={} ", newClient.getUsername());

        return clientAuthDto;
    }

    public ClientAuthDto login(String username, String password) throws Exception {

        Optional<ClientEntity> clientEntity = clientRepository.findByUsernameAndPassword(username, password);

        if (clientEntity.isEmpty()) {
            throw new ClientNotFoundException("client not found");
        }

        ClientAuthDto clientAuthDto = new ClientAuthDto();
        clientAuthDto.setApiKey(clientEntity.get().getApiKey());

        return clientAuthDto;

    }

    public void existsByApiKey(UUID apiKey) {

        boolean existsClient = clientRepository.existsByApiKey(apiKey);

        if (!existsClient) {
            throw new ClientNotFoundException("client not found");
        }
    }

}
