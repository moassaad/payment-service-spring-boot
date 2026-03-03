package com.nti.paymentservice.controller;

import com.nti.paymentservice.dto.PaymentResponse;
import com.nti.paymentservice.service.PaymentManagementService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentManagementService paymentService;

    private static final String API_KEY = "dfyuf-nfdfsh-nfnfh-fdjdhjf";

    // ✅ 4. List Customer Payments
    @GetMapping("/{customerId}")
    public ResponseEntity<?> listCustomerPayments(
            @RequestHeader("apikey") String apiKey,
            @PathVariable @Positive(message = "customerID must be positive") Long customerId,
            @RequestParam(required = false) String status
    ) {

        // API KEY validation
        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "invalid api key"));
        }

        List<PaymentResponse> payments =
                paymentService.getCustomerPayments(customerId, status);

        if (payments.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "no customer exists"));
        }

        return ResponseEntity.ok(payments);
    }


    // ✅ 5. Get Payment Details
    @GetMapping("/details/{id}")
    public ResponseEntity<?> getPaymentDetails(
            @RequestHeader("apikey") String apiKey,
            @PathVariable @Positive(message = "id must be positive") Long id
    ) {

        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "invalid api key"));
        }

        PaymentResponse payment = paymentService.getPaymentById(id);

        if (payment == null) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "no payment exists"));
        }

        return ResponseEntity.ok(payment);
    }
}