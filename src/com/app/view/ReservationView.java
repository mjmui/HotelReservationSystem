package com.app.view;

import com.app.model.Reservation;
import com.app.util.ConsoleUtils;
import static com.app.util.ConsoleUtils.CYAN;
import static com.app.util.ConsoleUtils.PURPLE;
import static com.app.util.ConsoleUtils.WHITE;
import static com.app.util.ConsoleUtils.YELLOW;
import static com.app.util.ConsoleUtils.RED;
import static com.app.util.ConsoleUtils.GREEN;
import java.util.List;

public class ReservationView{           
    public void displayReservationManagementOptions() {
        ConsoleUtils.printNewLines(15); 
        System.out.println(CYAN + "\t\t\t\t╔═══ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═══╗");
        System.out.println(CYAN + "\t\t\t  ✦•┈๑⋅⋯--- Reservation Management ---⋯⋅๑┈•✦");
        System.out.println(WHITE + "\t\t\t\t  📖 1. View All Reservations");
        System.out.println(WHITE + "\t\t\t\t  🆔 2. View Reservation by ID");
        System.out.println(WHITE + "\t\t\t\t  🛎 3. Add New Reservation");
        System.out.println(WHITE + "\t\t\t\t  📝 4. Update Reservation");
        System.out.println(WHITE + "\t\t\t\t  📥 5. Archive Reservation");
        System.out.println(WHITE + "\t\t\t\t  📂 6. Unarchive a reservation");
        System.out.println(WHITE + "\t\t\t\t  🗑 7. Delete Reservation");
        System.out.println(WHITE + "\t\t\t\t  ✔ 8. Accept Reservation");
        System.out.println(WHITE + "\t\t\t\t  ✖ 9. Cancel Reservation");
        System.out.println(WHITE + "\t\t\t\t  🏠 10. Back to Main Menu");
        System.out.println(WHITE + "\t\t\t\t  🔚 11. Exit");
        System.out.println(CYAN +"\t\t\t\t╚═══ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═══╝");
        ConsoleUtils.printNewLines(15); 
        System.out.print(YELLOW +"\t\t\t\t➤  Enter your choice: ");
    }
    
    public void displayReservationAdmin(Reservation reservation) {
        System.out.println(PURPLE + "╔═════════════════════════════════════════════ -ˋˏ *.·:· Reservation Details ·:·.* ˎˊ- ═══════════════════════════════════╗");
        System.out.printf(PURPLE + "%-15s | %-12s | %-10s | %-15s| %-15s | %-13s| %-10s | %-6s%n",
                WHITE + "Reservation ID", "Customer ID", "Room ID", "Check-In Date", "Check-Out Date", "Total Cost", "Status", "Archived");
        System.out.println(PURPLE + "╚═════════════════════════════════════════════════════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═════════════════════════════════════════════╝");
        System.out.printf(PURPLE + "%-15s | %-12s | %-10s | %-15s | %-15s | %-13s| %-10s | %-6s%n",
                WHITE + reservation.getReservationId(), reservation.getCustomerId(), reservation.getRoomId(), reservation.getReservationStartDate(),
                reservation.getReservationEndDate(), reservation.getReservationTotalCost(), reservation.getReservationTotalCost(), reservation.isArchived());
        ConsoleUtils.printNewLines(2);

    }
    
    public void displayReservationCustomer(Reservation reservation) {
        System.out.println(PURPLE + "╔═════════════════════════════════════════════ -ˋˏ *.·:· Reservation Details ·:·.* ˎˊ- ═══════════════════════════════════╗");
        System.out.printf(PURPLE + "%-15s | %-12s | %-10s | %-15s| %-15s | %-13s| %-10s",
                WHITE + "Reservation ID", "Customer ID", "Room ID", "Check-In Date", "Check-Out Date", "Total Cost", "Status");
        System.out.println(PURPLE + "╚═════════════════════════════════════════════════════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═════════════════════════════════════════════╝");
        System.out.printf(PURPLE + "%-15s | %-12s | %-10s | %-15s | %-15s | %-13s| %-10s",
                WHITE + reservation.getReservationId(), reservation.getCustomerId(), reservation.getRoomId(), reservation.getReservationStartDate(),
                reservation.getReservationEndDate(), reservation.getReservationTotalCost(), reservation.getReservationTotalCost());
        ConsoleUtils.printNewLines(2);

    }
    
    public void displayReservationsAdmin(List<Reservation> reservations) {
        System.out.println("\n--- Customer Reservations ---");
        for (Reservation reservation : reservations) {
            displayReservationAdmin(reservation);
            System.out.println(); 
        }
    }
    
    public void displayReservationsCustomer(List<Reservation> reservations) {
        System.out.println("\n--- Customer Reservations ---");
        for (Reservation reservation : reservations) {
            displayReservationCustomer(reservation);
            System.out.println(); 
        }
    }

    
    public void displayNoReservationsMessage() {
        System.out.println(RED +"\t\t\t「 ✦ ⚠ No reservations found.✦ 」");
    }

    public void displayReservationNotFoundMessage(int reservationId) {
        System.out.println(RED +"\t\t\t「 ✦ ⚠ Reservation with ID " + reservationId + " not found.✦ 」");
    }

    public void displayReservationAddedSuccess() {
        System.out.println(GREEN +"\t\t\t「 ✦ ✅ Reservation added successfully.✦ 」");
    }

    public void displayReservationAdditionFailed() {
        System.out.println(RED +"\t\t\t「 ✦ ⚠ Failed to add the reservation.✦ 」");
    }

    public void displayReservationUpdatedSuccess() {
        System.out.println(GREEN +"\t\t\t「 ✦ ✅ Reservation updated successfully.✦ 」");
    }

    public void displayReservationUpdateFailed() {
        System.out.println(RED +"\t\t\t「 ✦ ⚠ Failed to update the reservation.✦ 」");
    }

    public void displayReservationArchivedSuccess() {
        System.out.println(GREEN +"\t\t\t「 ✦ ✅ Reservation archived successfully.✦ 」");
    }

    public void displayReservationArchiveFailed() {
        System.out.println(RED +"\t\t\t「 ✦ ⚠ Failed to archive the reservation.✦ 」");
    }
    
    public void displayReservationUnarchivedSuccess() {
        System.out.println(GREEN +"\t\t\t「 ✦ ✅ Reservation unarchived successfully.✦ 」");
    }

    public void displayReservationUnarchiveFailed() {
        System.out.println(RED +"\t\t\t「 ✦ ⚠ Failed to unarchive the reservation.✦ 」");
    }

    public void displayReservationDeletedSuccess() {
        System.out.println(GREEN +"\t\t\t「 ✦ ✅ Reservation deleted successfully.✦ 」");
    }

    public void displayReservationDeletionFailed() {
        System.out.println(RED +"\t\t\t「 ✦ ⚠ Failed to delete the reservation.✦ 」");
    }

    public void displayReservationAcceptedSuccess() {
        System.out.println(GREEN +"\t\t\t「 ✦ ✅ Reservation accepted successfully.✦ 」");
    }

    public void displayReservationAcceptFailed() {
        System.out.println(RED +"\t\t\t「 ✦ ⚠ Failed to accept the reservation.✦ 」");
    }

    public void displayReservationCanceledSuccess() {
        System.out.println(GREEN +"\t\t\t「 ✦ ✅ Reservation canceled successfully.✦ 」");
    }

    public void displayReservationCancelFailed() {
        System.out.println(RED +"\t\t\t「 ✦ ⚠ Failed to cancel the reservation.✦ 」");
    }
}

