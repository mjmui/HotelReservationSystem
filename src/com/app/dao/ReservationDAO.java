package com.app.dao;

import com.app.model.Reservation;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public interface ReservationDAO {
    boolean addReservation(Reservation reservation);
    Reservation getReservationById(int reservationId);
    ArrayList<Reservation> getReservationsByCustomerId(int customerId);
    ArrayList<Reservation> getReservationsByRoomId(int roomId);
    ArrayList<Reservation> getAllReservations(boolean isArchived);
    boolean updateReservation(Reservation reservation);
    boolean archiveReservation(int reservationId);
    boolean unarchiveReservation(int reservationId);
    boolean deleteReservation(int reservationId);
    boolean isRoomAvailableForDates(int roomId, Date startDate, Date endDate);
    List<String> getUnavailableDates(int roomNumber);
    ArrayList<Reservation> getReservationsByDateRange(Date startDate, Date endDate);
}
