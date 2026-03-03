package com.nti.paymentservice.service;

import com.nti.paymentservice.entity.PaymentEntity;
import com.nti.paymentservice.entity.PaymentStatus;
import com.nti.paymentservice.exception.PaymentNotFoundException;
import com.nti.paymentservice.repository.PaymentRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundService {

    private final PaymentRepository paymentRepository;

    public PaymentEntity refundPayment(@NonNull Long paymentId) {
        PaymentEntity payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> {
                    return new PaymentNotFoundException("Payment with ID " + paymentId + " not found");
                });

        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setProcessedAt(LocalDate.now());

        payment = paymentRepository.save(payment);

        log.info("payment refunded id={}.", payment.getPaymentId());

        return payment;
    }
}