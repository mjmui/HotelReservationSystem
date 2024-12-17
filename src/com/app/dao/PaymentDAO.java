package com.app.dao;

import com.app.model.Payment;
import java.util.List;

public interface PaymentDAO {
    boolean addPayment(Payment payment);
    Payment getPaymentById(int paymentId);
    List<Payment> getAllPayments();
    Payment getPaymentByReservationId(int reservationId);
    boolean updatePayment(Payment payment);
    boolean archivePayment(int paymentId);
    boolean deletePayment(int paymentId);
}

