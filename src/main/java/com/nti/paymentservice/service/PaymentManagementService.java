package com.nti.paymentservice.service;

import com.nti.paymentservice.dto.PaymentResponse;
import com.nti.paymentservice.entity.PaymentEntity;
import com.nti.paymentservice.entity.PaymentStatus;
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
            try {
                PaymentStatus paymentStatus = PaymentStatus.valueOf(status.toUpperCase());
                payments = paymentRepository
                        .findByCustomerIdAndStatus(customerId, paymentStatus);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid payment status: " + status);
            }
        } else {
            payments = paymentRepository
                    .findByCustomerId(customerId);
        }

        if (payments.isEmpty()) {
            throw new RuntimeException("no customer exists");
        }

        return payments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ 5. Get Payment Details
    public PaymentResponse getPaymentById(Long id) {

        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no payment exists"));

        return mapToResponse(payment);
    }

    // ✅ Mapper Method
    private PaymentResponse mapToResponse(PaymentEntity payment) {

        PaymentResponse response = new PaymentResponse();

        response.setId(payment.getPaymentId());
        response.setStatus(payment.getStatus().name());
        response.setOrderId(payment.getOrderId());
        response.setCustomerId(payment.getCustomerId());
        response.setAmount(java.math.BigDecimal.valueOf(payment.getAmount()));
        response.setProcessedAt(payment.getProcessedAt());
        response.setCreatedAt(payment.getCreatedAt());

        return response;
    }
}
