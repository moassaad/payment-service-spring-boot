package com.nti.paymentservice.repository;

import com.nti.paymentservice.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity,Integer> {
 public boolean existsByApiKey(UUID apiKey);
 public Optional<ClientEntity> findByUsernameAndPassword(String username, String password);
}
