package com.app.dao;

import com.app.model.Room;
import java.util.List;

public interface RoomDAO {
    boolean addRoom(Room room);
    Room getRoomById(int roomId);
    List<Room> getAllRooms(boolean includeArchived);
    boolean updateRoom(Room room);
    boolean archiveRoom(int roomId);
    boolean unarchiveRoom(int roomId);
    boolean deleteRoom(int roomId);
    boolean roomNumberExists(int roomNumber);
    Room getRoomByNumber(int roomNumber);
}
