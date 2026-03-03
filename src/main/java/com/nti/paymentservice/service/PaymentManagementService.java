package com.nti.paymentservice.service;

import com.nti.paymentservice.dto.PaymentResponse;
import com.nti.paymentservice.entity.PaymentEntity;
import com.nti.paymentservice.entity.PaymentStatus;
import com.nti.paymentservice.exception.PaymentStatusException;
import com.nti.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentManagementService {

    private final PaymentRepository paymentRepository;

    // ✅ 4. List Customer Payments
    public List<PaymentResponse> getCustomerPayments(Long customerId, String status) {

        List<PaymentEntity> payments;

        if (status != null && !status.isBlank()) {

            PaymentStatus paymentStatus;

            try {
                paymentStatus = PaymentStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                // ✅ Use your custom exception
                throw new PaymentStatusException(status);
            }

            payments = paymentRepository
                    .findByCustomerIdAndStatus(customerId, paymentStatus);

        } else {
            payments = paymentRepository
                    .findByCustomerId(customerId);
        }

        if (payments == null || payments.isEmpty()) {
            throw new com.nti.paymentservice.exception.PaymentNotFoundException(
                    "No payments found for customer id: " + customerId);
        }

        return payments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ 5. Get Payment Details
    public PaymentResponse getPaymentById(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Payment ID cannot be null");
        }

        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new com.nti.paymentservice.exception.PaymentNotFoundException(
                        "no payment exists with id: " + id));

        return mapToResponse(payment);
    }

    // ✅ Mapper Method
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