package com.app.dao.impl;

import com.app.dao.ReservationDAO;
import com.app.exception.DataAccessException;
import com.app.model.Reservation;
import com.app.util.DbConnection;
import java.sql.Date;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public boolean addReservation(Reservation reservation) {
        String query = "INSERT INTO tblreservations (reservation_start_date, reservation_end_date, reservation_total_cost, reservation_status, room_id, customer_id, room_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setDate(1, reservation.getReservationStartDate());  
            prep.setDate(2, reservation.getReservationEndDate());  
            prep.setDouble(3, reservation.getReservationTotalCost());
            prep.setString(4, reservation.getReservationStatus());
            prep.setInt(5, reservation.getRoomId());
            prep.setInt(6, reservation.getCustomerId());
            prep.setInt(7, reservation.getRoomNumber());
            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error adding reservation", e);
        }
    }

    @Override
    public Reservation getReservationById(int reservationId) {
        String query = "SELECT * FROM tblreservations WHERE reservation_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setInt(1, reservationId);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return mapResultSetToReservation(result);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching reservation by reservation ID: " + reservationId, e);
        }
        return null;
    }

    @Override
    public ArrayList<Reservation> getReservationsByCustomerId(int customerId) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM tblreservations WHERE customer_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setInt(1, customerId);
            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    reservations.add(mapResultSetToReservation(result));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching reservations by customer ID: " + customerId, e);
        }
        return reservations;
    }
    

    @Override
    public ArrayList<Reservation> getReservationsByRoomId(int roomId) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM tblreservations WHERE room_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setInt(1, roomId);
            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    reservations.add(mapResultSetToReservation(result));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching reservations by room ID: " + roomId, e);
        }
        return reservations;
    }

    @Override
    public ArrayList<Reservation> getAllReservations(boolean includeArchived) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = includeArchived ? "SELECT * FROM tblreservations" : "SELECT * FROM tblreservations WHERE is_archived = false";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query);
             ResultSet result = prep.executeQuery()) {
            
            while (result.next()) {
                reservations.add(mapResultSetToReservation(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error fetching all reservations", e);
        }
        return reservations;
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        String query = "UPDATE tblreservations SET reservation_start_date = ?, reservation_end_date = ?, reservation_total_cost = ?, reservation_status = ?, room_id = ?, customer_id = ?, room_number = ? WHERE reservation_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setDate(1, reservation.getReservationStartDate());  
            prep.setDate(2, reservation.getReservationEndDate());
            prep.setDouble(3, reservation.getReservationTotalCost());
            prep.setString(4, reservation.getReservationStatus());
            prep.setInt(5, reservation.getRoomId());
            prep.setInt(6, reservation.getCustomerId());
            prep.setInt(7, reservation.getRoomNumber());
            prep.setInt(8, reservation.getReservationId());
            return prep.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new DataAccessException("Error updating reservation", e);
        }
    }

    @Override
    public boolean archiveReservation(int reservationId) {
        String query = "UPDATE tblreservations SET is_archived = TRUE WHERE reservation_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setInt(1, reservationId);
            return prep.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new DataAccessException("Error archiving reservation", e);
        }
    }
    
    @Override
    public boolean unarchiveReservation(int reservationId) {
        String query = "UPDATE tblreservations SET is_archived = FALSE WHERE reservation_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setInt(1, reservationId);
            return prep.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new DataAccessException("Error unarchiving reservation", e);
        }
    }

    @Override
    public boolean deleteReservation(int reservationId) {
        String query = "DELETE FROM tblreservations WHERE reservation_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setInt(1, reservationId);
            return prep.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting reservation", e);
        }
    }

    @Override
    public boolean isRoomAvailableForDates(int roomNumber, Date startDate, Date endDate) {
        String query = "SELECT 1 FROM tblreservations WHERE room_number = ? AND (reservation_start_date < ? AND reservation_end_date > ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            prep.setInt(1, roomNumber);
            prep.setDate(2, endDate);
            prep.setDate(3, startDate);
            try (ResultSet result = prep.executeQuery()) {
                return !result.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error checking room availability", e);
        }
    }
    
    @Override
    public List<String> getUnavailableDates(int roomNumber) {
        String query = "SELECT reservation_start_date, reservation_end_date FROM tblreservations WHERE room_number = ?";
        List<String> unavailableDates = new ArrayList<>();
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            prep.setInt(1, roomNumber);
            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    Date startDate = result.getDate("reservation_start_date");
                    Date endDate = result.getDate("reservation_end_date");
                    
                    unavailableDates.add(startDate.toString() + " to " + endDate.toString());
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching unavailable dates", e);
        }
        return unavailableDates;
    }


    @Override
    public ArrayList<Reservation> getReservationsByDateRange(Date startDate, Date endDate) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM tblreservations WHERE reservation_start_date >= ? AND reservation_end_date <= ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setDate(1, new java.sql.Date(startDate.getTime()));
            prep.setDate(2, new java.sql.Date(endDate.getTime()));
            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    reservations.add(mapResultSetToReservation(result));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving reservations by date range", e);
        }
        return reservations;
    }

    private Reservation mapResultSetToReservation(ResultSet result) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(result.getInt("reservation_id"));
        reservation.setReservationStartDate(result.getDate("reservation_start_date"));  
        reservation.setReservationEndDate(result.getDate("reservation_end_date"));     
        reservation.setReservationTotalCost(result.getDouble("reservation_total_cost"));
        reservation.setReservationStatus(result.getString("reservation_status"));
        reservation.setRoomNumber(result.getInt("room_number"));
        reservation.setRoomId(result.getInt("room_id"));
        reservation.setCustomerId(result.getInt("customer_id"));
        reservation.setIsArchived(result.getInt("is_archived") == 1);  
        return reservation;
    }
    

}
