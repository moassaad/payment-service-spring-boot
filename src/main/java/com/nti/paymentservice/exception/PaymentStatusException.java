package com.nti.paymentservice.exception;

public class PaymentStatusException extends RuntimeException {

    public PaymentStatusException(String status) {
        super("Invalid payment status: " + status);
    }
}
