package com.app.util;

import java.sql.Date;

public class ValidationUtils {
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    public static boolean isValidPhone(String phone) {
        String phoneRegex = "^\\+?[0-9]{10,15}$";
        return phone != null && phone.matches(phoneRegex);
    }

    public static boolean isNonNegative(int number) {
        return number >= 0;
    }
    
    public static boolean isNonNegative(double number) {
        return number >= 0;
    }

    public static boolean isNonEmptyString(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    public static boolean isPasswordLong(int pw) {
        return pw > 6;
    }
    
    public static boolean isPasswordLong(String pw) {
        return pw.length() > 6;
    }
    
    public static boolean isValidDateRange(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return false; 
        }
        return endDate.after(startDate); 
    }
}

