package com.app.dao;

import com.app.model.Hotel;
import com.app.model.Room;
import java.util.List;

public interface HotelDAO {
    boolean addHotel(Hotel hotel);
    Hotel getHotelById(int hotelId);
    List<Room> getRoomsByHotel(int hotelId);
    List<Hotel> getAllHotels(boolean isArchived);
    boolean updateHotel(Hotel hotel);
    boolean archiveHotel(int hotelId);
    boolean unarchiveHotel(int hotelId);
    boolean deleteHotel(int hotelId);
    String getHotelNameById(int hotelId);
}

