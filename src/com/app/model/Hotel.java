package com.app.model;

import com.app.util.CollectionUtils;
import java.util.ArrayList;

public class Hotel {
    
    private int hotelId;
    private String hotelName;
    private String hotelLocation;
    private ArrayList<Room> rooms;
    private boolean isArchived = false;
    

    public Hotel(int hotelId, String hotelName, String hotelLocation, ArrayList<Room> rooms) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.hotelLocation = hotelLocation;
        this.rooms = CollectionUtils.initializeList(rooms);
    }
    
    public Hotel() {
        this.rooms = new ArrayList<>();
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }
    
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.rooms = rooms != null ? rooms : new ArrayList<>();
    }
    
    public boolean isArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }
}

