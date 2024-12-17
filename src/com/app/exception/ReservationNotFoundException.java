package com.app.exception;

public class ReservationNotFoundException extends Exception {
    private int reservationId;

    public ReservationNotFoundException(int reservationId) {
        super("Reservation with ID " + reservationId + " was not found.");
        this.reservationId = reservationId;
    }

    public int getReservationId() {
        return reservationId;
    }
}
