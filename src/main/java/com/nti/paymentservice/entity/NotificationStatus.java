package com.nti.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NotificationStatus {
    PAYMENT_SUCCESS("PAYMERNT_SUCCESS"),
    PAYMENT_FAILED("PAYMERNT_FAILED");

    private final String name;
}
