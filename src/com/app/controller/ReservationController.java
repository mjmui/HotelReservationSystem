package com.app.controller;

import com.app.exception.ServiceException;
import com.app.model.Reservation;
import com.app.model.Room;
import com.app.service.ReservationService;
import com.app.util.ValidationUtils;
import com.app.view.AdminView;
import com.app.view.MainMenuView;
import com.app.view.ReservationView;
import static com.app.util.ConsoleUtils.YELLOW;
import static com.app.util.ConsoleUtils.RED;
import static com.app.util.ConsoleUtils.GREEN;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ReservationController {

    private final ReservationService reservationService;
    private final AdminView adminView;
    private final ReservationView reservationView;
    private final Scanner sc;
    private final MainMenuView mainMenuView;

    public ReservationController(ReservationService reservationService, AdminView adminView, Scanner sc, 
            MainMenuView mainMenuView, ReservationView reservationView) {
        this.reservationService = reservationService;
        this.adminView = adminView;
        this.reservationView = reservationView;
        this.sc = sc;
        this.mainMenuView = mainMenuView;
    }

    public void displayReservationMenu() {
        boolean back = false;
        while (!back) {
            try {
                reservationView.displayReservationManagementOptions();
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        displayAllReservations();
                        break;

                    case 2:
                        displayReservationById();
                        break;
                    case 3:
                        addReservationAdmin();
                        break;
                    case 4:
                        updateReservationAdmin();
                        break;
                    case 5:
                        archiveReservationAdmin();
                        break;
                    case 6:
                        unarchiveReservationAdmin();
                        break;
                    case 7:
                        deleteReservationAdmin();
                        break;
                    case 8:
                        acceptReservationAdmin();
                        break;
                    case 9:
                        cancelReservationAdmin();
                        break;
                    case 10:
                        back = true;
                        break;
                    case 11:
                        mainMenuView.displayExitMessage();
                        System.exit(0);
                        break;
                    default:
                        adminView.displayInvalidOptionMessage();
                }
            } catch (InputMismatchException e) {
                sc.nextLine(); 
                adminView.displayErrorMessage(RED+"Invalid input. Please enter a number for the menu choice.");
            }
        }
    }

    private void displayAllReservations() {
        try {
            System.out.print(YELLOW+"Would you like to include archived reservations? (yes/no): ");
            String includeArchivedInput = sc.nextLine().trim().toLowerCase();
            boolean includeArchived = includeArchivedInput.equals("yes");

            List<Reservation> reservations = reservationService.getAllReservations(includeArchived);
            if (reservations.isEmpty()) {
                reservationView.displayNoReservationsMessage();
            } else {
                for (Reservation reservation : reservations) {
                    reservationView.displayReservationAdmin(reservation);
                }
            }
        } catch (ServiceException e) {
            adminView.displayErrorMessage(RED+"Failed to retrieve reservations: " + e.getMessage());
        }
    }


    public void displayReservationById() {
        try {
            System.out.print(YELLOW+"\t\t\t Enter reservation ID: ");
            int reservationId = sc.nextInt();
            sc.nextLine();

        if (ValidationUtils.isNonNegative(reservationId)) {
                Reservation reservation = reservationService.getReservationById(reservationId);
                if (reservation != null) {
                    reservationView.displayReservationAdmin(reservation);
                } else {
                    reservationView.displayReservationNotFoundMessage(reservationId);
                }
            } else {
                System.out.println(RED+"\t\t\t Error: Reservation ID must be a positive number.");
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED+"\t\t\t Invalid input. Please enter a valid reservation ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }
    
    public void displayCustomerReservationById(int customerId) {
        try {
            
            List<Reservation> reservations = reservationService.getReservationsByCustomerId(customerId);
            
            if (reservations.isEmpty()) {
                System.out.println(RED+"\t\t\t No reservations found for the customer.");
            } else {
                reservationView.displayReservationsCustomer(reservations);
            }
        } catch (ServiceException e) {
            System.out.println(RED+"\t\t\t An error occurred while fetching reservations: " + e.getMessage());
        }
    }
    

    public void addReservationAdmin() {
        try {
            System.out.print("Enter customer ID: ");
            int customerId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Room Number: ");
            int roomNum = sc.nextInt();
            sc.nextLine();

            showAvailableDates(roomNum);  

            System.out.print("Enter check-in date (YYYY-MM-DD): ");
            java.sql.Date checkInDate = java.sql.Date.valueOf(sc.nextLine());

            System.out.print("Enter check-out date (YYYY-MM-DD): ");
            java.sql.Date checkOutDate = java.sql.Date.valueOf(sc.nextLine());

           
            if (!reservationService.isRoomAvailableForDates(roomNum, checkInDate, checkOutDate)) {
                System.out.println("The room is not available for the selected dates. Please try again.");
                return;
            }

            
            Room room = reservationService.getRoomByNumber(roomNum);
            if (room == null) {
                throw new ServiceException("Room not found.");
            }

            Reservation reservation = new Reservation();
            reservation.setRoomId(room.getRoomId());
            reservation.setCustomerId(customerId);
            reservation.setRoomNumber(roomNum);
            reservation.setReservationStartDate(checkInDate);
            reservation.setReservationEndDate(checkOutDate);
            reservation.setReservationStatus("Pending");

            
            reservationService.calculateAndSetTotalCost(reservation);
            System.out.println("Reservation Total Cost: " + reservation.getReservationTotalCost());

            
            System.out.print("Please pay the required amount: ");
            double payment = sc.nextDouble();
            sc.nextLine(); 
            if (payment < reservation.getReservationTotalCost()) {
                System.out.println("Payment not enough. Please pay the full amount.");
                return;
            } else {
                double change = payment - reservation.getReservationTotalCost();
                System.out.println("Payment successful! Your change is: " + change);
            }

            
            boolean added = reservationService.addReservation(reservation);
            if (added) {
                reservationView.displayReservationAddedSuccess();
            } else {
                reservationView.displayReservationAdditionFailed();
            }

        } catch (DateTimeParseException e) {
            adminView.displayErrorMessage("Invalid date format. Please use YYYY-MM-DD.");
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage("Invalid input. Please enter the correct data type.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage("Error adding reservation: " + e.getMessage());
        }
    }

    
     public void addReservationCustomer(int customerId) {
        try {
            System.out.print(YELLOW+"\t\t\t Enter Room Number: ");
            int roomNum = sc.nextInt();
            sc.nextLine();

            showAvailableDates(roomNum);

            System.out.print(YELLOW+"\t\t\t Enter Check-In Date (yyyy-mm-dd): ");
            java.sql.Date checkInDate = java.sql.Date.valueOf(sc.nextLine());

            System.out.print(YELLOW+"\t\t\t Enter Check-Out Date (yyyy-mm-dd): ");
            java.sql.Date checkOutDate = java.sql.Date.valueOf(sc.nextLine());

            if (!reservationService.isRoomAvailableForDates(roomNum, checkInDate, checkOutDate)) {
                System.out.println(RED+"\t\t\t The room is not available for the selected dates. Please try again.");
                return; 
            }

            
            Room room = reservationService.getRoomByNumber(roomNum); 
            if (room == null) {
                throw new ServiceException(RED+"\t\t\t Room not found.");
            }

            Reservation reservation = new Reservation();
            reservation.setRoomId(room.getRoomId()); 
            reservation.setCustomerId(customerId);
            reservation.setRoomNumber(roomNum);
            reservation.setReservationStartDate(checkInDate);
            reservation.setReservationEndDate(checkOutDate);
            reservation.setReservationStatus(GREEN+"Pending");
            
            reservationService.calculateAndSetTotalCost(reservation);
            System.out.println(YELLOW+"\t\t\t Reservation Payment Amount: " + reservation.getReservationTotalCost());
            System.out.print(YELLOW+"\t\t\t Please pay the required amount: ");
            double payment = sc.nextDouble();
            if (payment < reservation.getReservationTotalCost()){
                System.out.println(RED+"\t\t\t Payment not enough.");
                return;
            } else {
                double change = payment - reservation.getReservationTotalCost();
                System.out.println(YELLOW+"\t\t\t Your change is " + change);
                System.out.println(GREEN+"\t\t\t Successful payment!");
            }
            reservationService.addReservation(reservation);
            System.out.println(GREEN+"\t\t\t Reservation added successfully!");

        } catch (InputMismatchException e) {
            System.out.println(GREEN+"\t\t\t Invalid input format. Please try again.");
            sc.nextLine();
        } catch (ServiceException e) {
            System.out.println(RED+"\t\t\t Error adding reservation: " + e.getMessage());
        }
    }



    private void showAvailableDates(int roomNum) {
        
        List<String> unavailableDates = reservationService.getUnavailableDates(roomNum);
        if (!unavailableDates.isEmpty()) {
            System.out.println(YELLOW+"\t\t\t This room is unavailable on the following dates:");
            for (String date : unavailableDates) {
                System.out.println(RED + "\t\t\t\t"+date);
            }
        } else {
            System.out.println(YELLOW+"\t\t\t This room is available for all dates.");
        }
    }




    public void updateReservationAdmin() {
        try {
            System.out.print(YELLOW+"\t\t\t Enter reservation ID to update: ");
            int updateReservationId = sc.nextInt();
            sc.nextLine();

            Reservation reservationToUpdate = reservationService.getReservationById(updateReservationId);
            if (reservationToUpdate == null) {
                reservationView.displayReservationNotFoundMessage(updateReservationId);
                return;
            }

            System.out.print(YELLOW+"\t\t\t Enter new reservation status (Accepted or Cancelled, leave blank to keep current): ");
            String statusInput = sc.nextLine();
            if (!statusInput.isBlank()) {
                if (statusInput.equalsIgnoreCase(GREEN+"\t\t\t Accepted") || statusInput.equalsIgnoreCase(RED+"Cancelled")) {
                    reservationToUpdate.setReservationStatus(statusInput);
                } else {
                    adminView.displayErrorMessage(RED+"\t\t\t Invalid status. Please enter 'Accepted' or 'Cancelled'.");
                    return;
                }
            }

            System.out.print(YELLOW+"\t\t\t Enter new check-in date (leave blank to keep current, YYYY-MM-DD): ");
            String checkInInput = sc.nextLine();
            if (!checkInInput.isBlank()) {
                LocalDate checkInLocalDate = LocalDate.parse(checkInInput);
                reservationToUpdate.setReservationStartDate(java.sql.Date.valueOf(checkInLocalDate));
            }

            System.out.print(YELLOW+"\t\t\t Enter new check-out date (leave blank to keep current, YYYY-MM-DD): ");
            String checkOutInput = sc.nextLine();
            if (!checkOutInput.isBlank()) {
                LocalDate checkOutLocalDate = LocalDate.parse(checkOutInput);
                reservationToUpdate.setReservationEndDate(java.sql.Date.valueOf(checkOutLocalDate));
            }

            
            if (!checkInInput.isBlank() || !checkOutInput.isBlank()) {
                reservationService.calculateAndSetTotalCost(reservationToUpdate);
            } else {
                System.out.print(YELLOW+"\t\t\t Enter new total cost (leave blank to keep current): ");
                String costInput = sc.nextLine();
                if (!costInput.isBlank()) {
                    try {
                        double newCost = Double.parseDouble(costInput);
                        reservationToUpdate.setReservationTotalCost(newCost);
                    } catch (NumberFormatException e) {
                        adminView.displayErrorMessage(RED+"\t\t\t Invalid cost input. Please enter a valid number.");
                        return;
                    }
                }
            }

            boolean updated = reservationService.updateReservation(reservationToUpdate);
            if (updated) {
                reservationView.displayReservationUpdatedSuccess();
            } else {
                reservationView.displayReservationUpdateFailed();
            }
        } catch (DateTimeParseException e) {
            adminView.displayErrorMessage(RED+"\t\t\t Invalid date format. Please use YYYY-MM-DD.");
        } catch (InputMismatchException e) {
            sc.nextLine(); 
            adminView.displayErrorMessage(RED+"\t\t\t Invalid input. Please enter the correct data type.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }
    
    public void updateReservationCustomer(int customerId) {
        try {
            System.out.print(YELLOW+"\t\t\t Enter reservation ID to update: ");
            int updateReservationId = sc.nextInt();
            sc.nextLine();

            Reservation reservationToUpdate = reservationService.getReservationById(updateReservationId);
            if (reservationToUpdate == null || reservationToUpdate.getCustomerId() != customerId) {
                reservationView.displayReservationNotFoundMessage(updateReservationId);
                return;
            }

            System.out.print(YELLOW+"\t\t\t Enter new check-in date (leave blank to keep current, YYYY-MM-DD): ");
            String checkInInput = sc.nextLine();
            if (!checkInInput.isBlank()) {
                LocalDate checkInLocalDate = LocalDate.parse(checkInInput);
                reservationToUpdate.setReservationStartDate(java.sql.Date.valueOf(checkInLocalDate));
            }

            System.out.print(YELLOW+"\t\t\t Enter new check-out date (leave blank to keep current, YYYY-MM-DD): ");
            String checkOutInput = sc.nextLine();
            if (!checkOutInput.isBlank()) {
                LocalDate checkOutLocalDate = LocalDate.parse(checkOutInput);
                reservationToUpdate.setReservationEndDate(java.sql.Date.valueOf(checkOutLocalDate));
            }

            
            if (!checkInInput.isBlank() || !checkOutInput.isBlank()) {
                reservationService.calculateAndSetTotalCost(reservationToUpdate);
            } else {
                System.out.print(YELLOW+"\t\t\t Enter new total cost (leave blank to keep current): ");
                String costInput = sc.nextLine();
                if (!costInput.isBlank()) {
                    try {
                        double newCost = Double.parseDouble(costInput);
                        reservationToUpdate.setReservationTotalCost(newCost);
                    } catch (NumberFormatException e) {
                        System.out.println(RED+"\t\t\t Invalid cost input. Please enter a valid number.");
                        return;
                    }
                }
            }

            boolean updated = reservationService.updateReservation(reservationToUpdate);
            if (updated) {
                System.out.println(GREEN+"\t\t\t Reservation updated successfully!");
            } else {
                System.out.println(RED+"\t\t\t Failed to update reservation.");
            }
        } catch (DateTimeParseException e) {
            System.out.println(RED+"\t\t\t Invalid date format. Please use YYYY-MM-DD.");
        } catch (InputMismatchException e) {
            sc.nextLine(); 
            System.out.println(RED+"\t\t\t Invalid input. Please enter the correct data type.");
        } catch (ServiceException e) {
            System.out.println(RED+"\t\t\t Error updating reservation: " + e.getMessage());
        }
    }


    private void archiveReservationAdmin() {
        try {
            System.out.print(YELLOW+"\t\t\t Enter reservation ID to archive: ");
            int reservationId = sc.nextInt();
            sc.nextLine();

            boolean archived = reservationService.archiveReservation(reservationId);
            if (archived) {
                reservationView.displayReservationArchivedSuccess();
            } else {
                reservationView.displayReservationArchiveFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED+"\t\t\t Invalid input. Please enter a valid reservation ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }

    private void unarchiveReservationAdmin() {
        try {
            System.out.print(YELLOW+"\t\t\t Enter reservation ID to unarchive: ");
            int reservationId = sc.nextInt();
            sc.nextLine();

            boolean unarchived = reservationService.unarchiveReservation(reservationId);
            if (unarchived) {
                reservationView.displayReservationUnarchivedSuccess();
            } else {
                reservationView.displayReservationUnarchiveFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED+"\t\t\t Invalid input. Please enter a valid reservation ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }

    private void deleteReservationAdmin() {
        try {
            System.out.print(YELLOW+"\t\t\t Enter reservation ID to delete: ");
            int reservationId = sc.nextInt();
            sc.nextLine();

            boolean deleted = reservationService.deleteReservation(reservationId);
            if (deleted) {
                reservationView.displayReservationDeletedSuccess();
            } else {
                reservationView.displayReservationDeletionFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED+"\t\t\t Invalid input. Please enter a valid reservation ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }

    private void acceptReservationAdmin() {
        try {
            System.out.print(YELLOW+"\t\t\t Enter reservation ID to accept: ");
            int reservationId = sc.nextInt();
            sc.nextLine();

            boolean accepted = reservationService.acceptReservation(reservationId);
            if (accepted) {
                reservationView.displayReservationAcceptedSuccess();
            } else {
                reservationView.displayReservationAcceptFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED+"\t\t\t Invalid input. Please enter a valid reservation ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }

    private void cancelReservationAdmin() {
        try {
            System.out.print(YELLOW+"\t\t\t Enter reservation ID to cancel: ");
            int reservationId = sc.nextInt();
            sc.nextLine();

            boolean canceled = reservationService.cancelReservation(reservationId);
            if (canceled) {
                reservationView.displayReservationCanceledSuccess();
            } else {
                reservationView.displayReservationCancelFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED+"\t\t\t Invalid input. Please enter a valid reservation ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }
    
    public void cancelReservationCustomer(int customerId) {
        try {
            System.out.print(YELLOW+"\t\t\t Enter Reservation ID to cancel: ");
            int reservationId = sc.nextInt();
            sc.nextLine(); 

            Reservation reservation = reservationService.getReservationById(reservationId);

            if (reservation != null && reservation.getCustomerId() == customerId) {
                
                boolean isCancelled = reservationService.cancelReservation(reservationId);

                if (isCancelled) {
                    System.out.println(GREEN+"\t\t\t Reservation cancelled successfully.");
                } else {
                    System.out.println(RED+"\t\t\t Unable to cancel reservation. Please check the Reservation ID and try again.");
                }
            } else {
                System.out.println(RED+"\t\t\t You can only cancel your own reservations.");
            }
        } catch (InputMismatchException e) {
            System.out.println(RED+"\t\t\t Invalid input. Please enter a valid Reservation ID.");
            sc.nextLine(); 
        } catch (ServiceException e) {
            System.out.println(RED+"\t\t\t An error occurred while cancelling the reservation: " + e.getMessage());
        }
    }

}
