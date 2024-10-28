/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.model;

public class Room {

    private int roomId;
    private String roomType;
    private int roomCapacity;
    private String roomDescription;
    private boolean isRoomAvailable;
    private double pricePerNight;
    private int hotelId;

    public Room(int roomId, String roomType, int roomCapacity, String roomDescription, double pricePerNight, int hotelId) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomCapacity = roomCapacity;
        this.roomDescription = roomDescription;
        this.isRoomAvailable = true;
        this.pricePerNight = pricePerNight;
        this.hotelId = hotelId;
    }
    
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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
    
    public boolean getRoomAvailability() {
        return isRoomAvailable;
    }

    public void setRoomAvailability(boolean availability) {
        this.isRoomAvailable = availability;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
    
    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}
