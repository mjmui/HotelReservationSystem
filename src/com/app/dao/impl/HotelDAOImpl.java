package com.app.dao.impl;

import com.app.dao.HotelDAO;
import com.app.exception.DataAccessException;
import com.app.model.Hotel;
import com.app.model.Room;
import com.app.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAOImpl implements HotelDAO {

    @Override
    public boolean addHotel(Hotel hotel) {
        String query = "INSERT INTO tblhotels (hotel_name, hotel_location) VALUES (?, ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setString(1, hotel.getHotelName());
            prep.setString(2, hotel.getHotelLocation());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error adding hotel", e);
        }
    }

    @Override
    public Hotel getHotelById(int hotelId) {
        String query = "SELECT * FROM tblhotels WHERE hotel_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, hotelId);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return mapResultSetToHotel(result);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching hotel by ID: " + hotelId, e);
        }
        return null;
    }
    
    @Override
    public String getHotelNameById(int hotelId) {
        String query = "SELECT hotel_name FROM tblhotels WHERE hotel_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, hotelId);

            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return result.getString("hotel_name");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving hotel name by ID", e);
        }
        return null; 
    }

    @Override
    public List<Hotel> getAllHotels(boolean includeArchived) {
        List<Hotel> hotels = new ArrayList<>();
        String query = includeArchived ? "SELECT * FROM tblhotels" : "SELECT * FROM tblhotels WHERE is_archived = false";
        
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query);
             ResultSet result = prep.executeQuery()) {

            while (result.next()) {
                hotels.add(mapResultSetToHotel(result));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching all hotels", e);
        }
        return hotels;
    }

    @Override
    public boolean updateHotel(Hotel hotel) {
        String query = "UPDATE tblhotels SET hotel_name = ?, hotel_location = ? WHERE hotel_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setString(1, hotel.getHotelName());
            prep.setString(2, hotel.getHotelLocation());
            prep.setInt(3, hotel.getHotelId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error updating hotel", e);
        }
    }

    @Override
    public boolean archiveHotel(int hotelId) {
        String query = "UPDATE tblhotels SET is_archived = TRUE WHERE hotel_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, hotelId);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error archiving hotel by hotel ID: " + hotelId, e);
        }
    }
    
    @Override
    public boolean unarchiveHotel(int hotelId) {
        String query = "UPDATE tblhotels SET is_archived = FALSE WHERE hotel_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, hotelId);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error unarchiving hotel by hotel ID: " + hotelId, e);
        }
    }

    @Override
    public boolean deleteHotel(int hotelId) {
        String query = "DELETE FROM tblhotels WHERE hotel_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, hotelId);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting hotel by hotel ID: " + hotelId, e);
        }
    }

    @Override
    public ArrayList<Room> getRoomsByHotel(int hotelId) {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM tblrooms WHERE hotel_id = ?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, hotelId);
            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    rooms.add(mapResultSetToRoom(result));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving rooms by hotel ID: " + hotelId, e);
        }
        return rooms;
    }

    private Hotel mapResultSetToHotel(ResultSet result) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setHotelId(result.getInt("hotel_id"));
        hotel.setHotelName(result.getString("hotel_name"));
        hotel.setHotelLocation(result.getString("hotel_location"));
        hotel.setIsArchived(result.getInt("is_archived") == 1);
        hotel.setRooms(getRoomsByHotel(hotel.getHotelId())); 
        return hotel;
    }


    private Room mapResultSetToRoom(ResultSet result) throws SQLException {
        Room room = new Room();
        room.setRoomId(result.getInt("room_id"));
        room.setRoomNumber(result.getInt("room_number"));
        room.setRoomType(result.getString("room_type"));
        room.setRoomCapacity(result.getInt("room_capacity"));
        room.setRoomDescription(result.getString("room_description"));
        room.setRoomPrice(result.getDouble("room_price"));
        room.setHotelId(result.getInt("hotel_id"));
        room.setIsArchived(result.getInt("is_archived") == 1);
        return room;
    }

}
