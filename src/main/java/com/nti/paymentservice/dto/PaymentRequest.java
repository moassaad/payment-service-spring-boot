package com.nti.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class PaymentRequest {

    @NotNull(message = "order_id is required")
    @Positive(message = "order_id must be positive")
    @JsonProperty("order_id")
    private Long orderId;

    @NotNull(message = "customer_id is required")
    @Positive(message = "customer_id must be positive")
    @JsonProperty("customer_id")
    private Long customerId;

    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.01", message = "amount must be greater than 0")
    @JsonProperty("amount")
    private BigDecimal amount;

    // Getters & Setters
}
