package com.nti.paymentservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "payment")
@Getter
@Setter
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId; // Unique identifier for each status record

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // Enum for status values (success, failure, pending, refunded)

    @Column(name = "order_id")
    private Long orderId; // ID for the order associated with this status

    @Column(name = "customer_id")
    private Long customerId; // ID for the customer associated with this order
    @Column(name = "client_id")
    private String clientId; // ID for the client associated with this order

    private Double amount; // Amount associated with the order status

    @Column(name = "processed_at")
    private LocalDate processedAt; // Date when the status was processed

    @Column(name = "created_at")
    private LocalDate createdAt; // Date when the status was created
}
