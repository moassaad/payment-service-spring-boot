package com.nti.paymentservice.service;

import com.nti.paymentservice.dto.PaymentResponse;
import com.nti.paymentservice.entity.PaymentEntity;
import com.nti.paymentservice.entity.PaymentStatus;
import com.nti.paymentservice.exception.PaymentNotFoundException;
import com.nti.paymentservice.exception.PaymentStatusException;
import com.nti.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentManagementService {

    private final PaymentRepository paymentRepository;

    public List<PaymentResponse> getCustomerPayments(Long customerId, String status) {

        List<PaymentEntity> payments;

        if (status != null && !status.isBlank()) {

            PaymentStatus paymentStatus;

            try {
                paymentStatus = PaymentStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new PaymentStatusException(status);
            }

            payments = paymentRepository
                    .findByCustomerIdAndStatus(customerId, paymentStatus);

        } else {
            payments = paymentRepository
                    .findByCustomerId(customerId);
        }

        if (payments == null || payments.isEmpty()) {
            throw new PaymentNotFoundException("No payments found for customer id: " + customerId);
        }
        log.info("payment list customer customerId={} count is count={} ", customerId, payments.size());
        return payments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PaymentResponse getPaymentById(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Payment ID cannot be null");
        }
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    return new PaymentNotFoundException("no payment exists with id: " + id);
                });

        log.info("find payment by id id={} ", payment.getPaymentId());
        return mapToResponse(payment);
    }

    private PaymentResponse mapToResponse(PaymentEntity payment) {

        PaymentResponse response = new PaymentResponse();

        response.setId(payment.getPaymentId());
        response.setStatus(payment.getStatus().name());
        response.setOrderId(payment.getOrderId());
        response.setCustomerId(payment.getCustomerId());

        if (payment.getAmount() != null) {
            response.setAmount(java.math.BigDecimal.valueOf(payment.getAmount()));
        }

        response.setProcessedAt(payment.getProcessedAt());
        response.setCreatedAt(payment.getCreatedAt());

        return response;
    }
}