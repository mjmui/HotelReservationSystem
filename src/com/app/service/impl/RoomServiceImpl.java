
package com.app.service.impl;

import com.app.dao.HotelDAO;
import com.app.dao.RoomDAO;
import com.app.dao.impl.HotelDAOImpl;
import com.app.dao.impl.RoomDAOImpl;
import com.app.exception.DataAccessException;
import com.app.exception.ServiceException;
import com.app.model.Room;
import com.app.service.RoomService;
import com.app.util.ValidationUtils;
import java.util.List;


public class RoomServiceImpl implements RoomService{
    private final RoomDAO roomDAO;
    private final HotelDAO hotelDAO;
    
    public RoomServiceImpl() {
            this.roomDAO = new RoomDAOImpl(); 
            this.hotelDAO = new HotelDAOImpl();
    }


    @Override
    public boolean addRoom(Room room) {
        if(!ValidationUtils.isNonNegative(room.getRoomNumber())){
             throw new ServiceException("Room number should not be less than 1.");
        }
        if(!ValidationUtils.isNonEmptyString(room.getRoomType())){
            throw new ServiceException("Room type cannot be empty");
        }
        if (!ValidationUtils.isNonEmptyString(room.getRoomDescription())){
            throw new ServiceException("Put valid description");
        }
        
        if (roomNumberExists(room)){
            throw new ServiceException("Room number already exists");
        }
        
        try {
            return roomDAO.addRoom(room);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to add room", e);
        }
    }
        

    @Override
    public Room getRoomById(int roomId) {
        try {
            Room room = roomDAO.getRoomById(roomId);
            if (room == null) {
                throw new ServiceException("Room with ID " + roomId + " not found");
            }
            return room;
        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving admin with ID: " + roomId, e);
        }
    }
    

    @Override
    public List<Room> getAllRooms(boolean includeArchived) {
        try {
            return roomDAO.getAllRooms(includeArchived);
        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving all rooms", e);
        }
    }
    

    @Override
    public boolean updateRoom(Room room) {
        try {
            return roomDAO.updateRoom(room);
        } catch (DataAccessException e) {
            throw new ServiceException("Error updating room", e);
        }
    }

    @Override
    public boolean archiveRoom(int roomId) {
        try {
            return roomDAO.archiveRoom(roomId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error archiving room with ID " + roomId, e);
        }
    }
    
    @Override
    public boolean unarchiveRoom(int roomId) {
        try {
            return roomDAO.unarchiveRoom(roomId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error unarchiving room with ID " + roomId, e);
        }
    }
    @Override
    public boolean deleteRoom(int roomId) {
        try {
            return roomDAO.deleteRoom(roomId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error deleting room with ID " + roomId, e);
        }
    }
    
    @Override
    public String fetchHotelName(int hotelId) {
        return hotelDAO.getHotelNameById(hotelId);
    }
    
    @Override
    public boolean roomNumberExists(Room room){
        try {
            return roomDAO.roomNumberExists(room.getRoomNumber());
        } catch (DataAccessException e) {
            throw new ServiceException("Error checking if room exists.", e);
        }
    }
    
}
