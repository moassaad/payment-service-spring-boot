package com.nti.paymentservice.controller;

import com.nti.paymentservice.dto.PaymentResponse;
import com.nti.paymentservice.dto.PaymentRequest;
import com.nti.paymentservice.service.NotificationService;
import com.nti.paymentservice.service.PaymentManagementService;
import com.nti.paymentservice.service.PaymentService;
import com.nti.paymentservice.service.RefundService;
import com.nti.paymentservice.entity.PaymentEntity;
import com.nti.paymentservice.exception.ClientNotFoundException;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentManagementService paymentManagementService;
    private final PaymentService paymentService;
    private final RefundService refundService;

    private static final String API_KEY = "dfyuf-nfdfsh-nfnfh-fdjdhjf";

    @PostMapping("")
    public PaymentEntity createPayment(@RequestHeader("apikey") String apiKey,
            @RequestBody @Valid PaymentRequest paymentRequest) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new ClientNotFoundException("API Key is required");
        }

        if (!API_KEY.equals(apiKey)) {
            throw new ClientNotFoundException("invalid api key");
        }

        return paymentService.createPayment(paymentRequest, apiKey);
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<Object> refundPayment(@RequestHeader("apikey") String apiKey,
            @PathVariable("id") Long paymentId) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new ClientNotFoundException("API Key is required");
        }

        if (!API_KEY.equals(apiKey)) {
            throw new ClientNotFoundException("invalid api key");
        }
        try {
            PaymentEntity refundedPayment = refundService.refundPayment(paymentId);
            return ResponseEntity.ok(refundedPayment);

        } catch (Exception e) {
            throw e;
        }

        // PaymentEntity refundedPayment = refundService.refundPayment(paymentId,
        // apiKey);
        // return ResponseEntity.ok(refundedPayment); // Return 200 OK with refunded
        // payment details
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> listCustomerPayments(
            @RequestHeader("apikey") String apiKey,
            @PathVariable @Positive(message = "customerID must be positive") Long customerId,
            @RequestParam(required = false) String status) {

        if (apiKey == null || apiKey.isEmpty()) {
            throw new ClientNotFoundException("API Key is required");
        }

        if (!API_KEY.equals(apiKey)) {
            throw new ClientNotFoundException("invalid api key");
        }

        List<PaymentResponse> payments = paymentManagementService.getCustomerPayments(customerId, status);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/details/{id}")
    public PaymentResponse getPaymentDetails(
            @RequestHeader("apikey") String apiKey,
            @PathVariable @Positive(message = "id must be positive") Long id) {

        if (apiKey == null || apiKey.isEmpty()) {
            throw new ClientNotFoundException("API Key is required");
        }

        if (!API_KEY.equals(apiKey)) {
            throw new ClientNotFoundException("invalid api key");
        }

        return paymentManagementService.getPaymentById(id);
    }
}