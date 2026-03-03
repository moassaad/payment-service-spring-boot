package com.nti.paymentservice.exception;

/**
 * Thrown when a requested payment (or payments) cannot be found.
 */
public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException() {
        super("Payment not found");
    }

    public PaymentNotFoundException(String message) {
        super(message);
    }

}
