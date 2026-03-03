package com.nti.paymentservice.controller;

import com.nti.paymentservice.dto.PaymentResponse;
import com.nti.paymentservice.dto.PaymentRequest;
import com.nti.paymentservice.service.PaymentManagementService;
import com.nti.paymentservice.service.PaymentService;
import com.nti.paymentservice.service.RefundService;
import com.nti.paymentservice.entity.PaymentEntity;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentManagementService paymentManagementService;
    private final PaymentService paymentService;
    private final RefundService refundService;

    private static final String API_KEY = "dfyuf-nfdfsh-nfnfh-fdjdhjf";

    @PostMapping("")
    public PaymentEntity createPayment(@RequestHeader("apikey") String apiKey, @RequestBody @Valid PaymentRequest paymentRequest) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API Key is required");
        }
        return paymentService.createPayment(paymentRequest, apiKey);
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<Object> refundPayment(@RequestHeader("apikey") String apiKey, @PathVariable("id") Long paymentId) {

        if (apiKey == null || apiKey.isEmpty()) {
            return ResponseEntity.badRequest().body("API Key is required");
        }

        try {
            PaymentEntity refundedPayment = refundService.refundPayment(paymentId);
            return ResponseEntity.ok(refundedPayment);

        } catch (Exception e) {
            throw e;
        }
    }


    @GetMapping("/{customerId}")
    public ResponseEntity<?> listCustomerPayments(
            @RequestHeader("apikey") String apiKey,
            @PathVariable @Positive(message = "customerID must be positive") Long customerId,
            @RequestParam(required = false) String status
    ) {

        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "invalid api key"));
        }

        List<PaymentResponse> payments =
                paymentManagementService.getCustomerPayments(customerId, status);

        if (payments.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "no customer exists"));
        }

        return ResponseEntity.ok(payments);
    }



    @GetMapping("/details/{id}")
    public ResponseEntity<?> getPaymentDetails(
            @RequestHeader("apikey") String apiKey,
            @PathVariable @Positive(message = "id must be positive") Long id
    ) {

        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "invalid api key"));
        }

        PaymentResponse payment = paymentManagementService.getPaymentById(id);

        if (payment == null) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "no payment exists"));
        }

        return ResponseEntity.ok(payment);
    }
}