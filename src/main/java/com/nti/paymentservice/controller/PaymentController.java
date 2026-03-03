package com.nti.paymentservice.controller;

import com.nti.paymentservice.dto.PaymentRequest;
import com.nti.paymentservice.dto.PaymentResponse;
import com.nti.paymentservice.entity.PaymentEntity;
import com.nti.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/api/v1/payments")
    public PaymentEntity createPayment(@RequestHeader("apikey") String apiKey, @RequestBody @Valid PaymentRequest paymentRequest) {

            if (apiKey == null || apiKey.isEmpty()) {
                throw new IllegalArgumentException("API Key is required");
            }

            return paymentService.createPayment(paymentRequest, apiKey);
    }
}
