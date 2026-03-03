package com.nti.paymentservice.service;

import com.nti.paymentservice.dto.PaymentRequest;
import com.nti.paymentservice.entity.PaymentEntity;
import com.nti.paymentservice.entity.PaymentStatus;
import com.nti.paymentservice.repository.PaymentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PaymentService {
    public final PaymentRepository paymentRepository;

    public PaymentEntity createPayment(@Valid PaymentRequest paymentRequest, String apiKey) {
        // TODO Auth

        boolean ex = paymentRepository.existsByCustomerIdAndOrderIdAndClientId(paymentRequest.getCustomerId(),
                paymentRequest.getOrderId(), apiKey);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setCustomerId(paymentRequest.getCustomerId());
        paymentEntity.setOrderId(paymentRequest.getOrderId());
        paymentEntity.setAmount(paymentRequest.getAmount());

        paymentEntity.setStatus(PaymentStatus.SUCCESS);
        paymentEntity.setProcessedAt(LocalDate.now());
        paymentEntity.setCreatedAt(LocalDate.now());
        paymentEntity.setClientId(apiKey);

        if (ex) {
            paymentEntity.setPaymentId(12345645L);
            return paymentEntity;
        }
        return paymentRepository.save(paymentEntity);

    }
}
