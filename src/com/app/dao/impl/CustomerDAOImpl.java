package com.app.dao.impl;

import com.app.dao.CustomerDAO;
import com.app.exception.DataAccessException;
import com.app.model.Customer;
import com.app.model.Reservation;
import com.app.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean addCustomer(Customer customer) {
        String query = "INSERT INTO tblcustomers (customer_name, customer_address, customer_phone, customer_email, customer_password) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DbConnection.getConnection(); 
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setString(1, customer.getCustomerName());
            prep.setString(2, customer.getCustomerAddress());
            prep.setString(3, customer.getCustomerPhone());
            prep.setString(4, customer.getCustomerEmail());
            prep.setString(5, customer.getCustomerPassword());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error adding customer", e);
        }
    }

    @Override
    public Customer getCustomerById(int customerId) {
        String query = "SELECT * FROM tblcustomers WHERE customer_id = ?";
        try (Connection con = DbConnection.getConnection(); 
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, customerId);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return mapToCustomer(result);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching customer by id: " + customerId, e);
        }
        return null;
    }
    
    
    @Override
    public List<Reservation> getReservationsByCustomerId(int customerId) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM tblreservations WHERE customer_id = ?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, customerId);
            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setReservationId(result.getInt("reservation_id"));
                    reservation.setRoomId(result.getInt("room_id"));
                    reservation.setCustomerId(result.getInt("customer_Id"));
                    reservation.setRoomNumber(result.getInt("room_number"));
                    reservation.setReservationStartDate(result.getDate("reservation_start_date"));
                    reservation.setReservationEndDate(result.getDate("reservation_end_date"));
                    reservation.setReservationTotalCost(result.getDouble("reservation_total_cost"));
                    reservation.setReservationStatus(result.getString("reservation_status"));
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching reservations for customer ID " + customerId, e);
        }
        return reservations;
    }


    @Override
    public List<Customer> getAllCustomers(boolean includeArchived) {
        String query = includeArchived ? "SELECT * FROM tblcustomers" : "SELECT * FROM tblcustomers WHERE is_archived = false";
        List<Customer> customers = new ArrayList<>();

        try (Connection con = DbConnection.getConnection(); 
             PreparedStatement prep = con.prepareStatement(query);
             ResultSet result = prep.executeQuery()) {

            while (result.next()) {
                customers.add(mapToCustomer(result));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching all customers", e);
        }
        return customers;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String query = "UPDATE tblcustomers SET customer_name = ?, customer_address = ?, customer_phone = ?, customer_email = ?, customer_password = ? WHERE customer_id = ?";
        try (Connection con = DbConnection.getConnection(); 
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setString(1, customer.getCustomerName());
            prep.setString(2, customer.getCustomerAddress());
            prep.setString(3, customer.getCustomerPhone());
            prep.setString(4, customer.getCustomerEmail());
            prep.setString(5, customer.getCustomerPassword());
            prep.setInt(6, customer.getCustomerId());

            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error updating customer", e);
        }
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        String query = "DELETE FROM tblcustomers WHERE customer_id = ?";
        try (Connection con = DbConnection.getConnection(); 
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, customerId);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting customer by ID: " + customerId, e);
        }
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        String query = "SELECT * FROM tblcustomers WHERE customer_email = ?";
        try (Connection con = DbConnection.getConnection(); 
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setString(1, email);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return mapToCustomer(result);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error getting customer by email: " + email, e);
        }
        return null;
    }

    @Override
    public boolean archiveCustomer(int customerId) {
        String query = "UPDATE tblcustomers SET is_archived = 1 WHERE customer_id = ?";
        try (Connection con = DbConnection.getConnection(); 
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, customerId);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error archiving customer ID: " + customerId, e);
        }
    }
    
    @Override
    public boolean unarchiveCustomer(int customerId) {
        String query = "UPDATE tblcustomers SET is_archived = 0 WHERE customer_id = ?";
        try (Connection con = DbConnection.getConnection(); 
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, customerId);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error unarchiving customer ID: " + customerId, e);
        }
    }
    
    @Override
    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM tblcustomers WHERE customer_email = ?";
        try (Connection con = DbConnection.getConnection(); 
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setString(1, email);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return result.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error checking email existence", e);
        }
        return false;
    }
    
    @Override
    public boolean login(String email, String password) {
        String query = "SELECT customer_password FROM tblcustomers WHERE customer_email = ?";
        try (Connection con = DbConnection.getConnection(); 
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setString(1, email);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    String storedPassword = result.getString("customer_password");
                    return storedPassword.equals(password);
                } else {
                    System.out.println("Customer with email " + email + " not found.");
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error logging in", e);
        } 
    }
    
    @Override
    public Customer getCustomerByPhone(String phone) {
        String query = "SELECT * FROM tblcustomers WHERE customer_phone = ?";
        try (Connection con = DbConnection.getConnection(); 
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setString(1, phone);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return mapToCustomer(result);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching customer by phone number: " + phone, e);
        }
        return null; 
    }
    
//    private ArrayList<Reservation> getReservationsByCustomerId(int customerId) {
//        String query = "SELECT * FROM tblreservations WHERE customer_id = ?";
//        ArrayList<Reservation> reservations = new ArrayList<>();
//        try (Connection con = DbConnection.getConnection(); 
//             PreparedStatement prep = con.prepareStatement(query);
//             ResultSet result = prep.executeQuery()) {
//
//            prep.setInt(1, customerId);
//            while (result.next()) {
//                Reservation reservation = new Reservation();
//                reservation.setReservationId(result.getInt("reservation_id"));
//                reservations.add(reservation);
//            }
//        } catch (SQLException e) {
//            throw new DataAccessException("Error fetching reservations by customer ID: " + customerId, e);
//        }
//        return reservations;
//    }

    private Customer mapToCustomer(ResultSet result) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(result.getInt("customer_id"));
        customer.setCustomerName(result.getString("customer_name"));
        customer.setCustomerEmail(result.getString("customer_email"));
        customer.setCustomerAddress(result.getString("customer_address"));
        customer.setCustomerPhone(result.getString("customer_phone"));
        customer.setCustomerPassword(result.getString("customer_password"));
        customer.setArchived(result.getInt("is_archived") == 1); 
        customer.setReservations(getReservationsByCustomerId(customer.getCustomerId()));
        return customer;
    }
}
