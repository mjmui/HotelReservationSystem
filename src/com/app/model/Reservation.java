package com.app.model;

import java.sql.Date;

public class Reservation {

    private int reservationId;
    private Date reservationStartDate;
    private Date reservationEndDate;
    private double reservationTotalCost;
    private String reservationStatus = "Pending";
    private int roomId;
    private int customerId;
    private int roomNumber;
    private boolean isArchived = false;

    public Reservation(int reservationId, Date reservationStartDate, Date reservationEndDate, double totalCost, String reservationStatus, int roomNumber, int roomId, int customerId) {
        this.reservationId = reservationId;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.reservationStatus = reservationStatus != null ? reservationStatus : "Pending";
        this.reservationTotalCost = totalCost;
        this.roomNumber = roomNumber;
        this.roomId = roomId;
        this.customerId = customerId;
    }
    
    public Reservation(){
        
    }
    
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Date getReservationStartDate() {
        return reservationStartDate;
    }

    public void setReservationStartDate(Date reservationStartDate) {
        this.reservationStartDate = reservationStartDate;
    }
    
    public Date getReservationEndDate() {
        return reservationEndDate;
    }

    public void setReservationEndDate(Date reservationEndDate) {
        this.reservationEndDate = reservationEndDate;
    }

    public double getReservationTotalCost() {
        return reservationTotalCost;
    }

    public void setReservationTotalCost(double reservationTotalCost) {
        this.reservationTotalCost = reservationTotalCost;
    }
    
    public String getReservationStatus() {
        return reservationStatus;
    }
    
    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public boolean isArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }
}

