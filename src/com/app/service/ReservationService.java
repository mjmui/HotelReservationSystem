package com.app.service;

import com.app.model.Reservation;
import com.app.model.Room;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public interface ReservationService {
    boolean addReservation(Reservation reservation);
    Reservation getReservationById(int reservationId);
    ArrayList<Reservation> getReservationsByCustomerId(int customerId);
    ArrayList<Reservation> getReservationsByRoomId(int roomId);
    Room getRoomById(int roomId);
    ArrayList<Reservation> getAllReservations(boolean isArchived);
    boolean updateReservation(Reservation reservation);
    boolean archiveReservation(int reservationId);
    boolean unarchiveReservation(int reservationId);
    boolean deleteReservation(int reservationId);
    boolean cancelReservation(int reservationId);
    boolean acceptReservation(int reservationId);
    boolean isRoomAvailableForDates(int roomNumber, Date startDate, Date endDate);
    List<String> getUnavailableDates(int roomNumber);
    Room getRoomByNumber(int roomNumber);
    ArrayList<Reservation> getReservationsByDateRange(Date startDate, Date endDate);
    void calculateAndSetTotalCost(Reservation reservation);
}
