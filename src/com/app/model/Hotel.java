/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.model;

import java.util.ArrayList;

public class Hotel {

    private int hotelId;
    private String hotelName;
    private String hotelLocation;
    private ArrayList<Room> rooms;

    public Hotel(int hotelId, String hotelName, String hotelLocation, ArrayList<Room> rooms) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.hotelLocation = hotelLocation;
        this.rooms = rooms;
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

    public ArrayList<Room> getAllRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
}
