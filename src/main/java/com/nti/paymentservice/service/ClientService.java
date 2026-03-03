package com.nti.paymentservice.service;

import com.nti.paymentservice.dto.ClientAuthDto;
import com.nti.paymentservice.entity.ClientEntity;
import com.nti.paymentservice.exception.ClientAlreadyExistsException;
import com.nti.paymentservice.exception.ClientNotFoundException;
import com.nti.paymentservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientAuthDto register(String username, String password) throws Exception {

        Optional<ClientEntity> clientEntity = clientRepository.findByUsernameAndPassword(username, password);

        if (clientEntity.isPresent()) {
            throw new ClientAlreadyExistsException("username already exists");
        }

        ClientEntity newClient = new ClientEntity();

        newClient.setUsername(username);
        newClient.setPassword(password);
        newClient.setApiKey(UUID.randomUUID());
        clientRepository.save(newClient);

        ClientAuthDto clientAuthDto = new ClientAuthDto();
        clientAuthDto.setApiKey(newClient.getApiKey());

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
