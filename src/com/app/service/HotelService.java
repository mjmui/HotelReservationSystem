package com.app.service;

import com.app.model.Hotel;
import com.app.model.Room;
import java.util.List;

public interface HotelService {
    boolean addHotel(Hotel hotel);
    Hotel getHotelById(int hotelId);
    List<Hotel> getAllHotels(boolean includeArchived);
    boolean updateHotel(Hotel hotel);
    boolean archiveHotel(int hotelId);
    boolean unarchiveHotel(int hotelId);
    boolean deleteHotel(int hotelId);
    List<Room> getRoomsByHotel(int hotelId);
}
