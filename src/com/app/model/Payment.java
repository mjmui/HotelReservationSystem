/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.model;

import java.util.Date;

public class Payment {

    private int paymentId;
    private String paymentMethod;
    private double payment;
    private Date paymentDate;
    private int reservationId;
    private String paymentStatus;

    public Payment(int paymentId, String paymentMethod, double payment, Date paymentDate, int reservationId, String paymentStatus) {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.payment = payment;
        this.paymentDate = paymentDate;
        this.reservationId = reservationId;
        this.paymentStatus = paymentStatus;
    }
    
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
