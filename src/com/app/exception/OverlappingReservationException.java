package com.app.exception;

import java.util.Date;

public class OverlappingReservationException extends Exception {
    private int roomId;
    private Date startDate;
    private Date endDate;

    public OverlappingReservationException(int roomId, Date startDate, Date endDate) {
        super("Room with ID " + roomId + " is already reserved from " + startDate + " to " + endDate + ".");
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getRoomId() {
        return roomId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}

