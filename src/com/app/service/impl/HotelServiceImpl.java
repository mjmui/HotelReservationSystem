package com.app.service.impl;

import com.app.dao.HotelDAO;
import com.app.dao.impl.HotelDAOImpl;
import com.app.exception.DataAccessException;
import com.app.exception.ServiceException;
import com.app.model.Hotel;
import com.app.model.Room;
import com.app.service.HotelService;

import java.util.List;

public class HotelServiceImpl implements HotelService {
    private final HotelDAO hotelDAO;

    public HotelServiceImpl() {
            this.hotelDAO = new HotelDAOImpl(); 
    }

    @Override
    public boolean addHotel(Hotel hotel) {
        if (hotel.getHotelLocation() == null || hotel.getHotelLocation().isBlank() || hotel.getHotelName() == null || hotel.getHotelName().isBlank()) {
        throw new ServiceException("Hotel name and location cannot be null or empty.");
    }

        try {
            return hotelDAO.addHotel(hotel);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to add hotel", e);
        }
    }

    @Override
    public Hotel getHotelById(int hotelId) {
        try {
            Hotel hotel = hotelDAO.getHotelById(hotelId);
            if (hotel == null) {
                throw new ServiceException("Hotel with ID " + hotelId + " not found.");
            }
            return hotel;
        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving hotel with ID: " + hotelId, e);
        }
    }

    @Override
    public List<Hotel> getAllHotels(boolean includeArchived) {
        try {
            return hotelDAO.getAllHotels(includeArchived);
        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving hotels", e);
        }
}


    @Override
    public boolean updateHotel(Hotel hotel) {
        if (hotel.getHotelName() == null || hotel.getHotelName().trim().isEmpty() ||
            hotel.getHotelLocation() == null || hotel.getHotelLocation().trim().isEmpty()) {
            throw new ServiceException("Hotel name and location cannot be null or empty.");
        }

        try {
            return hotelDAO.updateHotel(hotel);
        } catch (DataAccessException e) {
            throw new ServiceException("Error updating hotel", e);
        }
    }

    @Override
    public boolean archiveHotel(int hotelId) {
        try {
            return hotelDAO.archiveHotel(hotelId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error archiving hotel with ID: " + hotelId, e);
        }
    }
    
    @Override
    public boolean unarchiveHotel(int hotelId) {
        try {
            return hotelDAO.unarchiveHotel(hotelId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error unarchiving hotel with ID: " + hotelId, e);
        }
    }

    @Override
    public boolean deleteHotel(int hotelId) {
        try {
            if (hotelDAO.getHotelById(hotelId) == null) {
                throw new ServiceException("Hotel with ID " + hotelId + " does not exist.");
            }
            return hotelDAO.deleteHotel(hotelId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error deleting hotel with ID: " + hotelId, e);
        }
    }

    @Override
    public List<Room> getRoomsByHotel(int hotelId) {
        try {
            return hotelDAO.getRoomsByHotel(hotelId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving rooms for hotel with ID: " + hotelId, e);
        }
    }
}
