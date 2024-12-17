package com.app.exception;

public class InvalidPaymentAmountException extends Exception {
    private double amount;

    public InvalidPaymentAmountException(double amount) {
        super("Invalid payment amount: " + amount + ".");
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

