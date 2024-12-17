package com.app.exception;

public class RoomTypeNotAvailableException extends Exception {
    private String roomType;

    public RoomTypeNotAvailableException(String roomType) {
        super("Room type '" + roomType + "' is not available at this time.");
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }
}

