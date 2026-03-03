package com.nti.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PaymentResponse {

    private Long id;

    private String status;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("customer_id")
    private Long customerId;

    private BigDecimal amount;

    @JsonProperty("processed_at")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate processedAt;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdAt;
}