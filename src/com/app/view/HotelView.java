package com.app.view;

import com.app.model.Hotel;
import com.app.model.Room;
import static com.app.util.ConsoleUtils.PURPLE;
import static com.app.util.ConsoleUtils.WHITE;
import static com.app.util.ConsoleUtils.YELLOW;
import static com.app.util.ConsoleUtils.RED;
import static com.app.util.ConsoleUtils.GREEN;
import static com.app.util.ConsoleUtils.CYAN;
import com.app.util.ConsoleUtils;
import java.util.ArrayList;
import java.util.List;

public class HotelView {
    private static final int DESCRIPTION_LINE_WIDTH = 20;
    public void displayHotelManagementOptions() {
        
        ConsoleUtils.printNewLines(10);
        System.out.println(CYAN + "\t\t\t\t╔═══ -ˋˏ .·:·.⟐.·:·. ˎˊ- ═══╗");
        System.out.println(CYAN + "\t\t\t\t✦•┈๑ Hotel Management Menu: ๑┈•✦");
        System.out.println(WHITE + "\t\t\t\t🏢 1. View all hotels");
        System.out.println(WHITE + "\t\t\t\t🛈 2. View a hotel's information");
        System.out.println(WHITE + "\t\t\t\t🛎 3. Add a new hotel"); 
        System.out.println(WHITE + "\t\t\t\t📝 4. Update hotel details");
        System.out.println(WHITE + "\t\t\t\t📥 5. Archive a hotel");
        System.out.println(WHITE + "\t\t\t\t📂 6. Unarchive a hotel");
        System.out.println(WHITE + "\t\t\t\t🗑 7. Delete a hotel");
        System.out.println(WHITE + "\t\t\t\t↩ 8. Back");
        System.out.println(WHITE + "\t\t\t\t🔚 9. Exit");
        System.out.println(CYAN + "\t\t\t\t╚═══ -ˋˏ .·:·.⟐.·:·. ˎˊ- ═══╝");
        ConsoleUtils.printNewLines(15);
        System.out.print(YELLOW + "\t\t\t\t➤  Enter your choice: ");
    }
    
    public void displayHotel(Hotel hotel) {
        
    System.out.println(PURPLE + "\n╔══════════════════════════════════════════════════════ -ˋˏ .·:· Hotel Details ·:·. ˎˊ- ═══════════════════════════════════════════════════════╗");
    System.out.printf(PURPLE + "%-15s \t| %-15s \t\t| %-10s \t| %-10s%n", 
                      WHITE + "Hotel ID", "Hotel Name", "Hotel Address","Archived");
    List<String> wrappedLocation = wrapText(hotel.getHotelLocation(), DESCRIPTION_LINE_WIDTH);
    System.out.printf(PURPLE + "%-15s \t| %-15s \t| %-15s \t\t\t| %-10s%n", 
           WHITE + hotel.getHotelId(),hotel.getHotelName(), wrappedLocation.get(0), hotel.isArchived());
    
    for (int i = 1; i < wrappedLocation.size(); i++) {
                    System.out.printf(PURPLE + "%-60s | %-10s |%n", "", WHITE + wrappedLocation.get(i));
                }
    
 System.out.println(PURPLE + "\n╔════════════════════════════════════════════════════════ -ˋˏ .·:· Room Details ·:·. ˎˊ- ════════════════════════════════════════════════════════╗");
 System.out.printf(PURPLE + "%-15s | %-15s | %-15s | %-15s| %-15s| %-15s \t| %-25s%n", 
                      WHITE + "Room Number", "Room ID", "Room Type","Room Capacity","Room Price","Room Description","Archived");
        if (hotel.getRooms().isEmpty()) {
                System.out.println(RED + "\t\t\t「 ✦ ⚠ No rooms found for this hotel. ✦ 」");
                } else {
                    for (Room room : hotel.getRooms()) {
    
    List<String> wrappedDescription = wrapText(room.getRoomDescription(), DESCRIPTION_LINE_WIDTH);
    System.out.printf(PURPLE + "%-15s | %-15s | %-15s | %-15s| %-15s| %-15s | %-25s%n",
            WHITE + room.getRoomNumber(),room.getRoomId(),room.getRoomType(),room.getRoomCapacity(),room.getRoomPrice(),wrappedDescription.get(0),room.isArchived());
    for (int i = 1; i < wrappedDescription.size(); i++) {
                    System.out.printf(PURPLE + "%-87s | %-40s |%n", "",WHITE + wrappedDescription.get(i));
                }
    
                    }
                    
        }
        System.out.println(PURPLE + "╚═════════════════════════════════════════════════════════════════ -ˋˏ .·:·.⟐.·:·. ˎˊ- ═══════════════════════════════════════════════════════════╝");
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


    public void displayNoHotelsMessage() {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ No hotels available. ✦ 」");
    }

    public void displayHotelNotFoundMessage(int hotelId) {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ Hotel with ID " + hotelId + " not found. ✦ 」");
    }

    public void displayHotelAddedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✅ Hotel added successfully. ✦ 」");
    }

    public void displayHotelAdditionFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ Failed to add hotel. ✦ 」");
    }
    
    public void displayHotelArchivedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✅ Hotel archived successfully. ✦ 」");
    }

    public void displayHotelArchiveFailed() {
        System.out.println(RED + "\t\t\t 「 ✦ ⚠ Failed to archive hotel. ✦ 」");
    }

    public void displayHotelUnarchiveSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✅ Hotel unarchived successfully. ✦ 」");
    }

    public void displayHotelUnarchiveFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ Failed to unarchive hotel. ✦ 」");
    }

    public void displayHotelUpdatedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✅ Hotel updated successfully. ✦ 」");
    }

    public void displayHotelUpdateFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ Failed to update hotel. ✦ 」");
    }

    public void displayHotelDeletedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✅ Hotel deleted successfully. ✦ 」");
    }

    public void displayHotelDeletionFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ Failed to delete hotel. ✦ 」");
    }
}