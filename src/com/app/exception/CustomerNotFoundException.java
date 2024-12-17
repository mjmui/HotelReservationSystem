package com.app.exception;

public class CustomerNotFoundException extends Exception {
    private int customerId;

    public CustomerNotFoundException(int customerId) {
        super("Customer with ID " + customerId + " was not found.");
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }
}

