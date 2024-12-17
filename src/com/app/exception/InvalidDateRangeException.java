package com.app.exception;

import java.util.Date;

public class InvalidDateRangeException extends Exception {
    private Date startDate;
    private Date endDate;

    public InvalidDateRangeException(Date startDate, Date endDate) {
        super("Invalid date range: " + startDate + " to " + endDate + ".");
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}

