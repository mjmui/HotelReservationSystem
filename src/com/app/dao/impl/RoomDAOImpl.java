package com.app.dao.impl;

import com.app.dao.RoomDAO;
import com.app.exception.DataAccessException;
import com.app.model.Room;
import com.app.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    @Override
    public boolean addRoom(Room room) {
        String query = "INSERT INTO tblrooms (room_type, room_capacity, room_description, room_number, room_price, hotel_id, hotel_name) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setString(1, room.getRoomType());
            prep.setInt(2, room.getRoomCapacity());
            prep.setString(3, room.getRoomDescription());
            prep.setInt(4, room.getRoomNumber());
            prep.setDouble(5, room.getRoomPrice());
            prep.setInt(6, room.getHotelId());
            prep.setString(7, room.getHotelName());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error adding room", e);
        }
    }

    @Override
    public Room getRoomById(int roomId) {
        String query = "SELECT * FROM tblrooms WHERE room_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, roomId);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return mapToRoom(result);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching room by ID: " + roomId, e);
        }
        return null;
    }
    
    @Override
    public Room getRoomByNumber(int roomNumber) {
        String query = "SELECT * FROM tblrooms WHERE room_number = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, roomNumber);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return mapToRoom(result);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching room by number: " + roomNumber, e);
        }
        return null;
    }

    @Override
    public List<Room> getAllRooms(boolean includeArchived) {
        String query = includeArchived ? "SELECT * FROM tblrooms" : "SELECT * FROM tblrooms WHERE is_archived = false";
        List<Room> rooms = new ArrayList<>();
        
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query);
             ResultSet result = prep.executeQuery()) {

            while (result.next()) {
                rooms.add(mapToRoom(result));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching all rooms", e);
        }
        return rooms;
    }

    @Override
    public boolean updateRoom(Room room) {
        String query = "UPDATE tblrooms SET room_type = ?, room_capacity = ?, room_description = ?, room_number = ?, room_price = ?, hotel_id = ?, hotel_name = ? WHERE room_id = ?";
                try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setString(1, room.getRoomType());
            prep.setInt(2, room.getRoomCapacity());
            prep.setString(3, room.getRoomDescription());
            prep.setInt(4, room.getRoomNumber());
            prep.setDouble(5, room.getRoomPrice());
            prep.setInt(6, room.getHotelId());
            prep.setString(7, room.getHotelName());
            prep.setInt(8, room.getRoomId()); 
           

            int rowsAffected = prep.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                throw new DataAccessException("No rows were updated. Please check the room ID and data integrity.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error updating room: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean archiveRoom(int roomId) {
        String query = "UPDATE tblrooms SET is_archived = TRUE WHERE room_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, roomId);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error archiving room by room ID: " + roomId, e);
        }
    }
    
    @Override
    public boolean unarchiveRoom(int roomId) {
        String query = "UPDATE tblrooms SET is_archived = FALSE WHERE room_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, roomId);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error unarchiving room by room ID: " + roomId, e);
        }
    }

    @Override
    public boolean deleteRoom(int roomId) {
        String query = "DELETE FROM tblrooms WHERE room_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, roomId);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting room by room ID: " + roomId, e);
        }
    }
    
    @Override
    public boolean roomNumberExists(int roomNumber) {
        String query = "SELECT COUNT(*) FROM tblrooms WHERE room_number = ?";
        try (Connection con = DbConnection.getConnection(); 
             PreparedStatement prep = con.prepareStatement(query)) {

            prep.setInt(1, roomNumber);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return result.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error checking room existence", e);
        }
        return false;
    }

    private Room mapToRoom(ResultSet result) throws SQLException {
        Room room = new Room();
        room.setRoomId(result.getInt("room_id"));
        room.setRoomType(result.getString("room_type"));
        room.setRoomCapacity(result.getInt("room_capacity"));
        room.setRoomDescription(result.getString("room_description"));
        room.setRoomNumber(result.getInt("room_number"));
        room.setRoomPrice(result.getDouble("room_price"));
        room.setHotelId(result.getInt("hotel_id"));
        room.setHotelName(result.getString("hotel_name"));
        room.setIsArchived(result.getInt("is_archived") == 1); 
        return room;
    }
}