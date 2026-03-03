package com.nti.paymentservice.repository;

import com.nti.paymentservice.entity.PaymentEntity;
import com.nti.paymentservice.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    boolean existsByCustomerIdAndOrderIdAndClientId(Long customerId, Long orderId, String clientId);

    List<PaymentEntity> findByCustomerId(Long customerId);

    List<PaymentEntity> findByCustomerIdAndStatus(Long customerId, PaymentStatus status);
}