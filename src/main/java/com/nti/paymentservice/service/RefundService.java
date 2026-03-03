package com.nti.paymentservice.service;

import com.nti.paymentservice.entity.PaymentEntity;
import com.nti.paymentservice.entity.PaymentStatus;
import com.nti.paymentservice.exception.PaymentNotFoundException;
import com.nti.paymentservice.exception.PaymentRefundException;
import com.nti.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final PaymentRepository paymentRepository;

    public PaymentEntity refundPayment(Long paymentId) {
        PaymentEntity payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with ID " + paymentId + " not found"));

        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setProcessedAt(LocalDate.now());
        
        return paymentRepository.save(payment);
    }
}