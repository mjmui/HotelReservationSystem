package com.app.view;

import com.app.model.Customer;
import com.app.model.Reservation;
import com.app.model.Room;
import com.app.util.ConsoleUtils;
import java.util.ArrayList;
import java.util.List;

public class CustomerView extends ConsoleUtils {

    private static final int DESCRIPTION_LINE_WIDTH = 20;

    public void displayCustomerManagementOptions() {

        ConsoleUtils.printNewLines(10);
        System.out.println(CYAN + "\t\t\t\t╔════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ════╗");
        System.out.println("  \t\t\t\t 👥 Customer Management Menu:");
        System.out.println(WHITE + "  \t\t\t\t 👥 1. View all customers");
        System.out.println(WHITE + "  \t\t\t\t 👥 2. View a customer's information");
        System.out.println(WHITE + "  \t\t\t\t 👥 3. Add a new customer");
        System.out.println(WHITE + "  \t\t\t\t 👥 4. Update customer details");
        System.out.println(WHITE + "  \t\t\t\t 👥 5. Archive a customer");
        System.out.println(WHITE + "  \t\t\t\t 👥 6. Unarchive a customer");
        System.out.println(WHITE + "  \t\t\t\t 👥 7. Delete a customer");
        System.out.println(WHITE + "  \t\t\t\t 👥 8. Back");
        System.out.println(WHITE + "  \t\t\t\t 👥 9. Exit");
        System.out.println(CYAN + "\t\t\t\t╚════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ════╝");
        ConsoleUtils.printNewLines(15);
        System.out.print(YELLOW +"    \t\t\t\t➤ Enter your choice: ");
    }

    public void displayCustomer(Customer customer) {
        System.out.println(CYAN + "\n\t\t╔═══════════════════════════════════ -ˋˏ *.·:·Customer Details For " + customer.getCustomerId() + "·:·.* ˎˊ- ═════════════════════════════════════╗");
        List<String> wrappedDescription = wrapText(customer.getCustomerAddress(), DESCRIPTION_LINE_WIDTH);
        System.out.printf("\t\t%-15s | %-15s | %-15s \t| %-15s| %-15s \t| %-10s%n",
                WHITE + "Customer ID", "Name", "Email", "Phone", "Address", "Archived");
        System.out.printf("\t\t%-15s | %-15s | %-15s | %-15s | %-15s \t| %-10s%n",
                WHITE + customer.getCustomerId(), customer.getCustomerName(), customer.getCustomerEmail(), customer.getCustomerPhone(),
                wrappedDescription.get(0), customer.isArchived());

        System.out.println(CYAN + "\n\t\t═════════════════════════════════════════════ Reservation Details For " + customer.getCustomerId() + "═════════════════════════════════════════════");

        System.out.printf(CYAN + "\t\t%-15s | %-15s \t| %-15s | %-15s| %-15s \t| %-10s%n",
                WHITE + "Reservation ID", "Room ID", "Check-In Date", "Check-Out Date", "Total Cost", "Status");
        if (customer.getReservations().isEmpty()) {
            System.out.println(RED + "\t\t\t\t\t「 ✦ ❌  No reservations found for this customer. ❌ ✦ 」");
        } else {
            for (Reservation reservation : customer.getReservations()) {

                System.out.printf(CYAN + "\t\t%-15s | %-15s \t| %-15s | %-15s | ₱%-14.2f \t| %-10s%n",
                        WHITE + reservation.getReservationId(), reservation.getRoomId(), reservation.getReservationStartDate(), reservation.getReservationEndDate(),
                        reservation.getReservationTotalCost(), reservation.getReservationStatus());
            }
        }
        System.out.println(CYAN + "\t\t═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        ConsoleUtils.printNewLines(2);
    }

    public List<String> wrapText(String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + 1 <= maxWidth) {

                if (line.length() > 0) {
                    line.append(" ");
                }
                line.append(word);
            } else {

                lines.add(line.toString());
                line = new StringBuilder(word);
            }
        }
        if (line.length() > 0) {
            lines.add(line.toString());
        }

        return lines;
    }

    public void displayCustomerOptions() {
        ConsoleUtils.printNewLines(15);
        System.out.println(CYAN + "\t\t\t\t╔════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ════╗");
        System.out.println(CYAN + "\t\t\t\t Customer Menu:");
        System.out.println(WHITE + "\t\t\t\t 1. Register account");
        System.out.println(WHITE + "\t\t\t\t 2. Log in with existing account");
        System.out.println(WHITE + "\t\t\t\t 3. Back");
        System.out.println(WHITE + "\t\t\t\t 4. Exit");
        System.out.println(CYAN + "\t\t\t\t╚════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ════╝");
        ConsoleUtils.printNewLines(15);
        System.out.print(YELLOW + "\t\t\t\t➤  Enter your choice: ");
    }

    public void displayCustomerFunctionsOptions() {
        ConsoleUtils.printNewLines(15);
        System.out.println(CYAN + "\t\t\t\t╔════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ════╗");
        System.out.println(CYAN + "\t\t\t\t Customer Main Menu:");
        System.out.println(WHITE + "\t\t\t\t 1. View account details");
        System.out.println(WHITE + "\t\t\t\t 2. Manage Reservations");
        System.out.println(WHITE + "\t\t\t\t 3. Back");
        System.out.println(WHITE + "\t\t\t\t 4. Exit");
        System.out.println(CYAN + "\t\t\t\t╚════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ════╝");
        ConsoleUtils.printNewLines(15);

        System.out.print(YELLOW + "\t\t\t\t➤  Enter your choice: ");
    }

    public void displayReservationManagementOptions() {
        ConsoleUtils.printNewLines(15);
        System.out.println(CYAN + "\t\t\t\t╔════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ════╗");
        System.out.println(WHITE + "\t\t\t\t Customer Reservations Menu:");
        System.out.println(WHITE + "\t\t\t\t 1. View your reservations");
        System.out.println(WHITE + "\t\t\t\t 2. Book a reservation");
        System.out.println(WHITE + "\t\t\t\t 3. Cancel reservation");
        System.out.println(WHITE + "\t\t\t\t 4. Back");
        System.out.println(WHITE + "\t\t\t\t 5. Exit");
        System.out.println(CYAN + "\t\t\t\t╚════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ════╝");
        ConsoleUtils.printNewLines(15);
        System.out.print(YELLOW + "\t\t\t\t➤  Enter your choice: ");
    }

    public void displayBookingOptions() {
        ConsoleUtils.printNewLines(15);
        System.out.println(CYAN + "\t\t\t\t╔════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ════╗");
        System.out.println(WHITE + "\t\t\t\t Booking Menu:");
        System.out.println(WHITE + "\t\t\t\t 1. View all available rooms");
        System.out.println(WHITE + "\t\t\t\t 2. Back");
        System.out.println(WHITE + "\t\t\t\t 3. Exit");
        System.out.println(CYAN + "\t\t\t\t╚════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ════╝");
        ConsoleUtils.printNewLines(15);
        System.out.print(YELLOW + "\t\t\t\t➤  Enter your choice: ");
    }

    public void displayCustomerAccountDetails(Customer customer) {
        System.out.println(CYAN + "\n\n╔═════════════════════════════ -ˋˏ *.·:·Customer Details·:·.* ˎˊ- ═══════════════════════════════╗");
        System.out.printf(CYAN + "\t%-10s | %-15s \t\t\t| %-15s | %-15s ",
                WHITE + "Name", "Address", "Phone", "Email");
        System.out.println("");
        System.out.printf(CYAN + "\t%-10s | %-15s | %-15s | %-15s ",
                WHITE + customer.getCustomerName(), customer.getCustomerAddress(),
                customer.getCustomerPhone(), customer.getCustomerEmail());
        System.out.println("");
        System.out.println(CYAN + "╚══════════════════════════════════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═════════════════════════════════════════╝");
//        System.out.println("Account Details:");
//        System.out.println("Name: " + customer.getCustomerName());
//        System.out.println("Address: " + customer.getCustomerAddress());
//        System.out.println("Phone: " + customer.getCustomerPhone());
//        System.out.println("Email: " + customer.getCustomerEmail());
    }

    public void displayAvailableRooms(List<Room> availableRooms) {
        System.out.println("Available Rooms:");
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms are available at the moment.");
        } else {
            for (Room room : availableRooms) {
                System.out.println(CYAN + "\n\n╔═════════════════════════════ -ˋˏ *.·:·Rooms Available·:·.* ˎˊ- ═══════════════════════════════╗");
                System.out.printf(CYAN + "\t%-10s | %-15s | %-15s | %-15s | %-10s ",
                        WHITE + "Room ID", "Room Type", "Capacity", "Room Price", "Description");
                System.out.println(CYAN + "═══════════════════════════════════════════════════════════════════════════════════════");
                System.out.printf(CYAN + "\t%-10s | %-15s | %-15s | %-15s | %-10s",
                        WHITE + room.getRoomId(), room.getRoomNumber(),
                        room.getRoomType(), room.getRoomCapacity(), room.getRoomPrice(),
                        room.getRoomDescription());
                System.out.println(CYAN + "╚══════════════════════════════════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═════════════════════════════════════════╝");
                ConsoleUtils.printNewLines(2);
//                System.out.println("Room ID: " + room.getRoomId());
//                System.out.println("Room Type: " + room.getRoomType());
//                System.out.println("Capacity: " + room.getRoomCapacity());
//                System.out.println("Price per Night: " + room.getPricePerNight());
//                System.out.println("Description: " + room.getRoomDescription());
            }
        }
    }

    public void displayReservationDetails(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("You have no current reservations.");
        } else {
            for (Reservation reservation : reservations) {

                System.out.println(CYAN + "\n\n╔═════════════════════════════ -ˋˏ *.·:·Reservations·:·.* ˎˊ- ═══════════════════════════════╗");
                System.out.printf(CYAN + "\t%-10s | %-15s | %-15s | %-15s | %-10s ",
                        WHITE + "Reservation ID", "Room ID", "Start Date", "End Date", "Total Cost", "Status");
                System.out.println(CYAN + "═══════════════════════════════════════════════════════════════════════════════════════");
                System.out.printf("CYAN + \t%-10s | %-15s | %-15s | %-15s | %-10s",
                        WHITE + reservation.getReservationId(), reservation.getRoomId(),
                        reservation.getReservationStartDate(), reservation.getReservationEndDate(), reservation.getReservationTotalCost(),
                        reservation.getReservationStatus());
                System.out.println(CYAN + "╚══════════════════════════════════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═════════════════════════════════════════╝");
                ConsoleUtils.printNewLines(2);
//                System.out.println("Reservation ID: " + reservation.getReservationId());
//                System.out.println("Room ID: " + reservation.getRoomId());
//                System.out.println("Start Date: " + reservation.getStartDate());
//                System.out.println("End Date: " + reservation.getEndDate());
//                System.out.println("Total Cost: " + reservation.getTotalCost());
//                System.out.println("Status: " + reservation.getStatus());
            }
        }
    }

    public void displayNoCustomersMessage() {
        System.out.println(RED + "\t\t\t「 ✦ ❌ No customers found. ❌ ✦ 」");
    }

    public void displayAddCustomerPrompt() {
        System.out.println(YELLOW + "\t\t\t「Enter the details for the new customer:");
    }

    public void displayCustomerAddedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✔ Customer added successfully. ✔ ✦ 」");
    }

    public void displayCustomerAdditionFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ❌ Failed to add the customer. Please try again. ❌ ✦ 」");
    }

    public void displayUpdateCustomerPrompt() {
        System.out.println(YELLOW + "\t\t\t Enter the details to update the customer:");
    }

    public void displayCustomerNotFoundMessage() {
        System.out.println(RED + "\t\t\t「 ✦ ❌ Customer not found. Please check the ID and try again. ❌ ✦ 」");
    }

    public void displayCustomerUpdatedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✔ Customer updated successfully. ✔ ✦ 」");
    }

    public void displayCustomerUpdateFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ❌ Failed to update the customer. Please try again. ❌ ✦ 」");
    }

    public void displayCustomerArchivedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✔ Customer archived successfully. ✔ ✦ 」");
    }

    public void displayCustomerArchiveFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ❌ Failed to update the customer. Please try again. ❌ ✦ 」");
    }

    public void displayDeleteCustomerPrompt() {
        System.out.println(YELLOW + "\t\t\t Enter the ID of the customer to delete:");
    }

    public void displayCustomerDeletedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✔ Customer deleted successfully. ✔ ✦ 」");
    }

    public void displayCustomerDeletionFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ❌ Failed to delete the customer. Please try again. ❌ ✦ 」");
    }

    public void displayCustomerNotFoundMessage(int customerId) {
        System.out.println(RED + "\t\t\t「 ✦ ❌ Customer with ID " + customerId + " not found. Please check the ID and try again. ❌ ✦ 」");
    }

    public void displayCustomerUnarchivedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✅displayCustomerUnarchiveSuccess Customer unarchived successfully. ✦ 」");
    }

    public void displayCustomerUnarchiveFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ Failed to unarchive customer. ✦ 」");
    }
//    public void displayCustomerOptions(){
//        System.out.println("Invalid Option");
//    }

    public void displayInvalidOptionMessage() {
        System.out.println(RED + "\t\t\t Invalid Option");
    }

    public void displayRegistrationSuccessMessage() {
        System.out.println(GREEN + "\t\t\t Registration Successful");
    }
//    public void displayCustomerFunctionsOptions(){
//        System.out.println("Invalid option");
//    }

    public void displayRegistrationFailedMessage() {
        System.out.println(RED + "\t\t\t Invalid Option");
    }

    public void displayLoginSuccessMessage() {
        System.out.println(GREEN + "\t\t\t Login succesful");
    }

    public void displayLoginFailureMessage() {
        System.out.println(RED + "\t\t\t Login Failed");
    }
//    public void displayCustomerAccountDetails(Customer customer){
//        System.out.println("Invalid option");
//    }
//    public void displayReservationManagementOptions(){
//        System.out.println("Invalid option");
//    }

}
