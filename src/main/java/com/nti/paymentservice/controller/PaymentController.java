package com.nti.paymentservice.controller;

import com.nti.paymentservice.dto.PaymentRequest;
import com.nti.paymentservice.entity.PaymentEntity;
import com.nti.paymentservice.service.PaymentService;
import com.nti.paymentservice.service.RefundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final RefundService refundService;

    // Endpoint to create a payment
    @PostMapping("/api/v1/payments")
    public PaymentEntity createPayment(@RequestHeader("apikey") String apiKey, @RequestBody @Valid PaymentRequest paymentRequest) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API Key is required");
        }
        return paymentService.createPayment(paymentRequest, apiKey);
    }

    // Endpoint to process a refund
    @PostMapping("/api/v1/payments/{id}/refund")
    public ResponseEntity<Object> refundPayment(@RequestHeader("apikey") String apiKey, @PathVariable("id") Long paymentId) {
        // Check if API key is provided
        if (apiKey == null || apiKey.isEmpty()) {
            return ResponseEntity.badRequest().body("API Key is required");
        }

        // Attempt to refund the payment
        try {
            PaymentEntity refundedPayment = refundService.refundPayment(paymentId, apiKey);
            return ResponseEntity.ok(refundedPayment);  // Return 200 OK with refunded payment details

        } catch (Exception e) {
            // Exception handling is done globally, so no need for specific error responses here
            throw e;  // Let the global exception handler take care of it
        }
    }
}