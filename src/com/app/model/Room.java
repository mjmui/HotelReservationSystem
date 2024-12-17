package com.app.model;

public class Room {

    private int roomId;
    private int roomNumber;
    private String roomType;
    private int roomCapacity;
    private String roomDescription;
    private double roomPrice;
    private int hotelId;
    private String hotelName;
    private boolean isArchived = false;

    public Room(int roomId, int roomNumber, String roomType, int roomCapacity, String roomDescription, double roomPrice, int hotelId, String hotelName) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomCapacity = roomCapacity;
        this.roomDescription = roomDescription;
        this.roomPrice = roomPrice;
        this.hotelId = hotelId;
        this.hotelName = hotelName;
    }
    
    public Room(){
        
    }
    
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
        
    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public String getRoomType() {
        return roomType;
    }
    
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    
    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }
    
    public String getRoomDescription() {
        return roomDescription;
    }
    
    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }
    
    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
    
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
    
    public String getHotelName() {
        return hotelName;
    }

    public void setRoomNumber(String hotelName) {
        this.hotelName = hotelName;
    }
    
    public boolean isArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }
}

