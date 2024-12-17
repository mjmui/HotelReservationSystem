package com.app.service;

import com.app.model.Payment;
import java.util.List;
import java.util.Date;

public interface PaymentService {
    boolean addPayment(String paymentMethod, double amount, Date paymentDate, int reservationId);
    Payment getPaymentById(int paymentId);
    Payment getPaymentByReservationId(int reservationId);
    List<Payment> getAllPayments();
    boolean updatePayment(Payment payment);
    boolean archivePayment(int paymentId);
    boolean deletePayment(int paymentId);
    boolean processPayment(int reservationId, String paymentMethod, double amount);
}