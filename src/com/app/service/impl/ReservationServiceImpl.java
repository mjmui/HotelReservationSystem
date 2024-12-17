package com.app.service.impl;

import com.app.dao.ReservationDAO;
import com.app.dao.RoomDAO;
import com.app.exception.DataAccessException;
import com.app.exception.ServiceException;
import com.app.model.Reservation;
import com.app.model.Room;
import com.app.service.ReservationService;
import com.app.util.ValidationUtils;
import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationDAO reservationDAO;
    private final RoomDAO roomDAO;

    public ReservationServiceImpl(ReservationDAO reservationDAO, RoomDAO roomDAO) {
        this.reservationDAO = reservationDAO;
        this.roomDAO = roomDAO;
    }

    @Override
    public boolean addReservation(Reservation reservation) {
        try {
            validateReservation(reservation);
            if (!reservationDAO.isRoomAvailableForDates(reservation.getRoomId(), reservation.getReservationStartDate(), reservation.getReservationEndDate())) {
                throw new ServiceException("Room is not available for the specified dates.");
            }
            return reservationDAO.addReservation(reservation);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to add reservation", e);
        }
    }

    @Override
    public Reservation getReservationById(int reservationId) {
        try {
            if (!ValidationUtils.isNonNegative(reservationId)) {
                throw new ServiceException("Invalid reservation ID provided.");
            }
            return reservationDAO.getReservationById(reservationId);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve reservation by ID", e);
        }
    }
    
    @Override
    public Room getRoomByNumber(int roomNumber) {
        try {
            if (!ValidationUtils.isNonNegative(roomNumber)) {
                throw new ServiceException("Invalid reservation ID provided.");
            }
            return roomDAO.getRoomByNumber(roomNumber);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve reservation by number", e);
        }
    }

    @Override
    public ArrayList<Reservation> getReservationsByCustomerId(int customerId) {
        try {
            if (!ValidationUtils.isNonNegative(customerId)) {
                throw new ServiceException("Invalid customer ID provided.");
            }
            return reservationDAO.getReservationsByCustomerId(customerId);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve reservations by customer ID", e);
        }
    }

    @Override
    public ArrayList<Reservation> getReservationsByRoomId(int roomId) {
        try {
            if (!ValidationUtils.isNonNegative(roomId)) {
                throw new ServiceException("Invalid room ID provided.");
            }
            return reservationDAO.getReservationsByRoomId(roomId);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve reservations by room ID", e);
        }
    }
    
    @Override
    public Room getRoomById(int roomId) {
        try {
            if (!ValidationUtils.isNonNegative(roomId)) {
                throw new ServiceException("Invalid room ID provided.");
            }
            return roomDAO.getRoomById(roomId);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve room by ID", e);
        }
    }

    @Override
    public ArrayList<Reservation> getAllReservations(boolean isArchived) {
        try {
            return reservationDAO.getAllReservations(isArchived);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve all reservations", e);
        }
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        try {
            validateReservation(reservation);
            return reservationDAO.updateReservation(reservation);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to update reservation", e);
        }
    }

    @Override
    public boolean archiveReservation(int reservationId) {
        try {
            if (!ValidationUtils.isNonNegative(reservationId)) {
                throw new ServiceException("Invalid reservation ID provided.");
            }
            return reservationDAO.archiveReservation(reservationId);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to archive reservation", e);
        }
    }
    
    @Override
    public boolean unarchiveReservation(int reservationId) {
        try {
            if (!ValidationUtils.isNonNegative(reservationId)) {
                throw new ServiceException("Invalid reservation ID provided.");
            }
            return reservationDAO.unarchiveReservation(reservationId);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to unarchive reservation", e);
        }
    }

    @Override
    public boolean deleteReservation(int reservationId) {
        try {
            if (!ValidationUtils.isNonNegative(reservationId)) {
                throw new ServiceException("Invalid reservation ID provided.");
            }
            return reservationDAO.deleteReservation(reservationId);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to delete reservation", e);
        }
    }

    @Override
    public boolean isRoomAvailableForDates(int roomNumber, Date startDate, Date endDate) throws ServiceException {
        try {
            validateDates(startDate, endDate);
            return reservationDAO.isRoomAvailableForDates(roomNumber, startDate, endDate);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to check room availability", e);
        }
    }

    @Override
    public ArrayList<Reservation> getReservationsByDateRange(Date startDate, Date endDate) throws ServiceException {
        try {
            validateDates(startDate, endDate);
            return reservationDAO.getReservationsByDateRange(startDate, endDate);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve reservations by date range", e);
        }
    }
    
    @Override
    public List<String> getUnavailableDates(int roomNumber) {
        try {
            return reservationDAO.getUnavailableDates(roomNumber);
        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving unavailable dates", e);
        }
    }



    @Override
    public boolean cancelReservation(int reservationId) {
        try {
            if (!ValidationUtils.isNonNegative(reservationId)) {
                throw new ServiceException("Invalid reservation ID provided.");
            }
            Reservation reservation = reservationDAO.getReservationById(reservationId);
            if (reservation == null) {
                throw new ServiceException("Reservation not found.");
            }
            reservation.setReservationStatus("Canceled");
            return reservationDAO.updateReservation(reservation);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to cancel reservation", e);
        }
    }

    @Override
    public boolean acceptReservation(int reservationId) {
        try {
            if (!ValidationUtils.isNonNegative(reservationId)) {
                throw new ServiceException("Invalid reservation ID provided.");
            }
            Reservation reservation = reservationDAO.getReservationById(reservationId);
            if (reservation == null) {
                throw new ServiceException("Reservation not found.");
            }
            reservation.setReservationStatus("Accepted");
            return reservationDAO.updateReservation(reservation);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to accept reservation", e);
        }
    }
    
    @Override
    public void calculateAndSetTotalCost(Reservation reservation) {
        try {
            Room room = getRoomById(reservation.getRoomId()); // Use roomId here
            if (room == null) {
                throw new ServiceException("Room not found.");
            }

            long daysBetween = ChronoUnit.DAYS.between(
                reservation.getReservationStartDate().toLocalDate(),
                reservation.getReservationEndDate().toLocalDate()
            );

            double totalCost = daysBetween * room.getRoomPrice();
            reservation.setReservationTotalCost(totalCost);
        } catch (Exception e) {
            throw new ServiceException("Failed to calculate total cost: " + e.getMessage());
        }
    }

    private void validateReservation(Reservation reservation) throws ServiceException {
        if (reservation == null) {
            throw new ServiceException("Reservation cannot be null.");
        }
        if (!ValidationUtils.isNonEmptyString(reservation.getReservationStatus())) {
            throw new ServiceException("Status cannot be null or empty.");
        }
        if (!ValidationUtils.isNonNegative(reservation.getReservationTotalCost())) {
            throw new ServiceException("Total cost cannot be negative.");
        }
        if (!ValidationUtils.isNonNegative(reservation.getRoomId()) || !ValidationUtils.isNonNegative(reservation.getCustomerId())) {
            throw new ServiceException("Invalid room or customer ID provided.");
        }
        validateDates(reservation.getReservationStartDate(), reservation.getReservationEndDate());
    }
    
    private void validateDates(Date startDate, Date endDate) throws ServiceException {
        if (startDate == null || endDate == null) {
            throw new ServiceException("Start and end dates cannot be null.");
        }
        if (!ValidationUtils.isValidDateRange(startDate, endDate)) {
            throw new ServiceException("End date must be after start date.");
        }
    }
}
