package com.nti.paymentservice.repository;

import com.nti.paymentservice.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity,Integer> {

}
