package com.nti.paymentservice.repository;

import com.nti.paymentservice.entity.PaymentEntity;
import com.nti.paymentservice.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    List<PaymentEntity> findByCustomerId(Long customerId);

    List<PaymentEntity> findByCustomerIdAndStatus(Long customerId, PaymentStatus status);
}