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

    // Refund the payment by its ID
    public PaymentEntity refundPayment(Long paymentId, String apiKey) {
        // Retrieve the payment from the repository (throws PaymentNotFoundException if not found)
        PaymentEntity payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with ID " + paymentId + " not found"));

        // Check if the payment has already been refunded
        if (PaymentStatus.REFUNDED.equals(payment.getStatus())) {
            throw new PaymentRefundException("Payment has already been refunded.");
        }

        // Update the payment status to refunded and set the refunded date
        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setProcessedAt(LocalDate.now());

        // Save the updated payment back to the database
        return paymentRepository.save(payment);
    }
}