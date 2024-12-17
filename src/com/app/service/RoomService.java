package com.app.service;

import com.app.model.Room;
import java.util.List;

public interface RoomService{
    boolean addRoom(Room room);
    Room getRoomById(int roomId);
    List<Room> getAllRooms(boolean includeArchived);
    boolean updateRoom(Room room);
    boolean archiveRoom(int roomId);
    boolean unarchiveRoom(int roomId);
    boolean deleteRoom(int roomId);
    boolean roomNumberExists(Room room);
    String fetchHotelName(int hotelId);
}