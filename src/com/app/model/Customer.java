package com.app.model;

import java.util.ArrayList;
import com.app.util.CollectionUtils;
import java.util.List;

public class Customer {
    
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String customerPassword;
    private List<Reservation> reservations;
    private boolean isArchived = false;

    public Customer(int customerId, String customerName, String customerAddress, String customerPhone, String customerEmail, String customerPassword, ArrayList<Reservation> reservations) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.reservations = CollectionUtils.initializeList(reservations);
    }
    
    public Customer() {
    this.reservations = new ArrayList<>(); 
    }
    
    public Customer(int customerId, String customerName, String customerAddress, String customerPhone, String customerEmail, String customerPassword) {
        this(customerId, customerName, customerAddress, customerPhone, customerEmail, customerPassword, new ArrayList<>());
    }
    
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }
    
    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations != null ? reservations : new ArrayList<>();
    }
    
    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean isArchived) { 
        this.isArchived = isArchived;
    }
}
