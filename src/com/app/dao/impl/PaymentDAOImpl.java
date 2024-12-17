package com.app.dao.impl;

import com.app.dao.PaymentDAO;
import com.app.model.Payment;
import com.app.exception.DataAccessException;
import com.app.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public boolean addPayment(Payment payment) {
        String query = "INSERT INTO tblpayments (payment_method, payment_amount, payment_status, payment_date, reservation_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setString(1, payment.getPaymentMethod());
            prep.setDouble(2, payment.getPaymentAmount());
            prep.setString(3, payment.getPaymentStatus());
            prep.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
            prep.setInt(4, payment.getReservationId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error adding payment", e);
        }
    }

    @Override
    public Payment getPaymentById(int paymentId) {
        String query = "SELECT * FROM tblpayments WHERE payment_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, paymentId);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return mapToPayment(result);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching payment by ID: " + paymentId, e);
        }
        return null;
    }

    @Override
    public Payment getPaymentByReservationId(int reservationId) {
        String query = "SELECT * FROM tblpayments WHERE reservation_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, reservationId);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return mapToPayment(result);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching payment by reservation ID: " + reservationId, e);
        }
        return null;
    }

    @Override
    public List<Payment> getAllPayments() {
        String query = "SELECT * FROM tblpayments";
        List<Payment> payments = new ArrayList<>();
        
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query);
             ResultSet result = prep.executeQuery()) {

            while (result.next()) {
                payments.add(mapToPayment(result));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching all payments", e);
        }
        return payments;
    }

    @Override
    public boolean updatePayment(Payment payment) {
        String query = "UPDATE tblpayments SET payment_method = ?, payment_amount = ?, payment_date = ?, reservation_id = ? WHERE payment_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            prep.setString(1, payment.getPaymentMethod());
            prep.setDouble(2, payment.getPaymentAmount());
            prep.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
            prep.setInt(4, payment.getReservationId());
            prep.setInt(5, payment.getPaymentId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error updating payment", e);
        }
    }

    @Override
    public boolean archivePayment(int paymentId) {
        String query = "UPDATE tblpayments SET is_archived = true WHERE payment_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, paymentId);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error archiving payment by ID: " + paymentId, e);
        }
    }

    @Override
    public boolean deletePayment(int paymentId) {
        String query = "DELETE FROM tblpayments WHERE payment_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, paymentId);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting payment by ID: " + paymentId, e);
        }
    }

    private Payment mapToPayment(ResultSet result) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentId(result.getInt("payment_id"));
        payment.setPaymentMethod(result.getString("payment_method"));
        payment.setPayment(result.getDouble("payment"));
        payment.setPaymentDate(result.getDate("payment_date"));
        payment.setReservationId(result.getInt("reservation_id"));
        payment.setPaymentStatus(result.getString("payment_status"));
        return payment;
    }
}
