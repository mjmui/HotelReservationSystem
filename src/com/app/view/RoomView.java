package com.app.view;

import com.app.model.Room;
import com.app.util.ConsoleUtils;
import static com.app.util.ConsoleUtils.CONSOLE_WIDTH;
import static com.app.util.ConsoleUtils.CYAN;
import static com.app.util.ConsoleUtils.GREEN;
import static com.app.util.ConsoleUtils.PURPLE;
import static com.app.util.ConsoleUtils.RED;
import static com.app.util.ConsoleUtils.WHITE;
import static com.app.util.ConsoleUtils.YELLOW;

public class RoomView {
    
    
    public void displayRoomManagementOptions() {
  
        ConsoleUtils.printNewLines(10);
        System.out.println(CYAN + "\n\n\t\t\t\t╔═══ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═══╗");
        System.out.println(CYAN + "\t\t\t\t✦•┈๑ Room Management Menu: ๑┈•✦");
        System.out.println(WHITE + "\t\t\t\t🚪 1. View all rooms");
        System.out.println(WHITE + "\t\t\t\t🛈 2. View a room's information");
        System.out.println(WHITE + "\t\t\t\t🛎 3. Add a new room");
        System.out.println(WHITE + "\t\t\t\t📝 4. Update room details");
        System.out.println(WHITE + "\t\t\t\t📥 5. Archive a room");
        System.out.println(WHITE + "\t\t\t\t📂 6. Unarchive a room");
        System.out.println(WHITE + "\t\t\t\t🗑 7. Delete a room");
        System.out.println(WHITE + "\t\t\t\t↩ 8. Back");
        System.out.println(WHITE + "\t\t\t\t🔚 9. Exit");
        System.out.println(CYAN + "\t\t\t\t╚═══ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═══╝");
        ConsoleUtils.printNewLines(15);
        System.out.print(YELLOW +"\t\t\t\t➤  Enter your choice: ");
    }
    
    public void displayRoom(Room room) {
        ConsoleUtils.printNewLines(2);
        System.out.println(PURPLE + "╔═════════════════════════════ -ˋˏ *.·:·Room Details For Room " + room.getRoomId() + "·:·.* ˎˊ- ═══════════════════════════════╗");
        System.out.printf(PURPLE + "\t%-10s | %-15s | %-15s | %-15s | %-10s | %-15s | %-10s%n", 
                          WHITE + "Room ID", "Hotel ID", "Room Number", "Type", "Capacity", "Price", "Is Archived");
        System.out.println(PURPLE + "═══════════════════════════════════════════════════════════════════════════════════════");
        System.out.printf(PURPLE + "\t%-10s | %-15s | %-15s | %-15s | %-10s | ₱%-15.2f | %-10s%n", 
                          WHITE + room.getRoomId(), room.getHotelId(), room.getRoomNumber(), 
                          room.getRoomType(), room.getRoomCapacity(), room.getRoomPrice(), 
                          room.isArchived() ? "Yes" : "No");
        System.out.println(PURPLE + "╚══════════════════════════════════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ════════════════════════════════════════╝");
    }
    
    public void displayRoomCustomer(Room room) {
        ConsoleUtils.printNewLines(2);
        System.out.println(PURPLE + "╔═════════════════════════════ -ˋˏ *.·:·Room Details For Room " + room.getRoomId() + "·:·.* ˎˊ- ═══════════════════════════════╗");
        System.out.printf(PURPLE + "\t%-10s | %-15s | %-15s | %-15s | %-10s | %-15s", 
                          WHITE + "Room ID", "Hotel ID", "Room Number", "Type", "Capacity", "Price");
        System.out.println(PURPLE + "═══════════════════════════════════════════════════════════════════════════════════════");
        System.out.printf(PURPLE + "\t%-10s | %-15s | %-15s | %-15s | %-10s | ₱%-15.2f", 
                          WHITE + room.getRoomId(), room.getHotelId(), room.getRoomNumber(), 
                          room.getRoomType(), room.getRoomCapacity(), room.getRoomPrice());
        System.out.println(PURPLE + "╚══════════════════════════════════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═════════════════════════════════════════╝");
        ConsoleUtils.printNewLines(2);
    }
    
    public void displayNoRoomsMessage() {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ No rooms available. ✦ 」");
    }

    public void displayRoomNotFoundMessage(int roomId) {
        System.out.println(GREEN + "\t\t\t「 ✦ ⚠ Room with ID " + roomId + " not found. ✦ 」");
    }

    public void displayRoomAddedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✅ Room added successfully. ✦ 」");
    }

    public void displayRoomAdditionFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ Failed to add room. ✦ 」");
    }
    
    public void displayInvalidRoomIdMessage() {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ Please input a valid room ID. ✦ 」");
    }

    public void displayRoomUpdatedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✅ Room updated successfully. ✦ 」");
    }

    public void displayRoomUpdateFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ Failed to update room. ✦ 」");
    }
    
    public void displayRoomArchivedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✅ Room archived successfully. ✦ 」");
    }

    public void displayRoomArchiveFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ Failed to archived room. ✦ 」");
    }
    
    public void displayRoomUnarchivedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✅ Room unarchived successfully. ✦ 」");
    }

    public void displayRoomUnarchiveFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ Failed to unarchive room. ✦ 」");
    }
    
    public void displayRoomDeletedSuccess() {
        System.out.println(GREEN + "\t\t\t「 ✦ ✅ Room deleted successfully. ✦ 」");
    }

    public void displayRoomDeletionFailed() {
        System.out.println(RED + "\t\t\t「 ✦ ⚠ Failed to delete room. ✦ 」");
    }
}

