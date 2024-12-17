package com.app.exception;

public class InsufficientRoomCapacityException extends Exception {
    private int roomId;
    private int requestedCapacity;

    public InsufficientRoomCapacityException(int roomId, int requestedCapacity) {
        super("Room with ID " + roomId + " cannot accommodate the requested capacity of " + requestedCapacity + " guests.");
        this.roomId = roomId;
        this.requestedCapacity = requestedCapacity;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getRequestedCapacity() {
        return requestedCapacity;
    }
}

