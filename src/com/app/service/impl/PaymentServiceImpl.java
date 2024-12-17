package com.app.service.impl;

import com.app.dao.PaymentDAO;
import com.app.model.Payment;
import com.app.exception.DataAccessException;
import com.app.exception.ServiceException;
import com.app.service.PaymentService;
import com.app.util.ValidationUtils;
import java.util.Date;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentDAO paymentDAO;

    public PaymentServiceImpl(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @Override
    public boolean addPayment(String paymentMethod, double amount, Date paymentDate, int reservationId) {
        if (!ValidationUtils.isNonNegative(amount)) {
            throw new ServiceException("Payment amount must be positive");
        }
        if (paymentDate == null) {
            throw new ServiceException("Payment date cannot be null");
        }

        Payment payment = new Payment();
        payment.setPaymentMethod(paymentMethod);
        payment.setPayment(amount);
        payment.setPaymentDate(paymentDate);
        payment.setReservationId(reservationId);

        try {
            return paymentDAO.addPayment(payment);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to add payment", e);
        }
    }

    @Override
    public Payment getPaymentById(int paymentId) {
        try {
            Payment payment = paymentDAO.getPaymentById(paymentId);
            if (payment == null) {
                throw new ServiceException("Payment with ID " + paymentId + " not found");
            }
            return payment;
        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving payment with ID: " + paymentId, e);
        }
    }

    @Override
    public Payment getPaymentByReservationId(int reservationId) {
        try {
            Payment payment = paymentDAO.getPaymentByReservationId(reservationId);
            if (payment == null) {
                throw new ServiceException("No payment found for reservation ID " + reservationId);
            }
            return payment;
        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving payment for reservation ID: " + reservationId, e);
        }
    }

    @Override
    public List<Payment> getAllPayments() {
        try {
            return paymentDAO.getAllPayments();
        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving all payments", e);
        }
    }

    @Override
    public boolean updatePayment(Payment payment) {
        if (!ValidationUtils.isNonNegative(payment.getPaymentAmount())) {
            throw new ServiceException("Payment amount must be positive");
        }
        if (payment.getPaymentDate() == null) {
            throw new ServiceException("Payment date cannot be null");
        }

        try {
            return paymentDAO.updatePayment(payment);
        } catch (DataAccessException e) {
            throw new ServiceException("Error updating payment with ID: " + payment.getPaymentId(), e);
        }
    }

    @Override
    public boolean archivePayment(int paymentId) {
        try {
            return paymentDAO.archivePayment(paymentId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error archiving payment with ID: " + paymentId, e);
        }
    }

    @Override
    public boolean deletePayment(int paymentId) {
        try {
            if (paymentDAO.getPaymentById(paymentId) == null) {
                throw new ServiceException("Payment with ID " + paymentId + " does not exist.");
            }
            return paymentDAO.deletePayment(paymentId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error deleting payment with ID: " + paymentId, e);
        }
    }

    @Override
    public boolean processPayment(int reservationId, String paymentMethod, double amount) {
        if (!ValidationUtils.isNonNegative(amount)) {
            throw new ServiceException("Payment amount must be positive");
        }
        Date paymentDate = new Date();

        Payment payment = new Payment();
        payment.setPaymentMethod(paymentMethod);
        payment.setPayment(amount);
        payment.setPaymentDate(paymentDate);
        payment.setReservationId(reservationId);

        try {
            return paymentDAO.addPayment(payment);
        } catch (DataAccessException e) {
            throw new ServiceException("Error processing payment for reservation ID: " + reservationId, e);
        }
    }
}
