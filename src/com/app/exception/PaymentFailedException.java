package com.app.exception;

public class PaymentFailedException extends Exception {
    private int reservationId;
    private String paymentMethod;

    public PaymentFailedException(int reservationId, String paymentMethod) {
        super("Payment failed for reservation ID " + reservationId + " using method " + paymentMethod + ".");
        this.reservationId = reservationId;
        this.paymentMethod = paymentMethod;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}

