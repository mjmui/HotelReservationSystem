package com.app.controller;

import com.app.model.Room;
import com.app.service.RoomService;
import com.app.view.AdminView;
import com.app.view.MainMenuView;
import com.app.exception.ServiceException;
import static com.app.util.ConsoleUtils.GREEN;
import static com.app.util.ConsoleUtils.RED;
import static com.app.util.ConsoleUtils.YELLOW;
import com.app.util.ValidationUtils;
import com.app.view.RoomView;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RoomController {

    private final RoomService roomService;
    private final AdminView adminView;
    private final MainMenuView mainMenuView;
    private final RoomView roomView;
    private final Scanner sc;

    public RoomController(RoomService roomService, AdminView adminView, MainMenuView mainMenuView, Scanner sc) {
        this.roomService = roomService;
        this.adminView = adminView;
        this.mainMenuView = mainMenuView;
        this.roomView = new RoomView();
        this.sc = sc;
    }

   public void displayRoomMenu() {
        boolean back = false;
        while (!back) {
            roomView.displayRoomManagementOptions();
            try {
                int choice = sc.nextInt();
                sc.nextLine(); 
                switch (choice) {
                    case 1:
                        displayAllRooms();
                        break;
                    case 2:
                        displayRoomById();
                        break;
                    case 3:
                        addRoom();
                        break;
                    case 4:
                        updateRoom();
                        break;
                    case 5:
                        archiveRoom();
                        break;
                    case 6:
                        unarchiveRoom();
                        break;
                    case 7:
                        deleteRoom();
                        break;
                    case 8:
                        back = true;
                        break;
                    case 9:
                        mainMenuView.displayExitMessage();
                        System.exit(0);
                        break;
                    default:
                        adminView.displayInvalidOptionMessage();
                }
            } catch (InputMismatchException e) {
                sc.nextLine(); 
                adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid option.");
            } catch (Exception e) {
                adminView.displayErrorMessage(RED + "\t\t\tAn unexpected error occurred: " + e.getMessage());
            }
        }
    }

    public void displayAllRooms() {
        try {
            System.out.print(YELLOW + "\t\t\t Would you like to include archived rooms? (yes/no): ");
            String includeArchivedInput = sc.nextLine().trim().toLowerCase();
            boolean includeArchived = includeArchivedInput.equals("yes");

            List<Room> rooms = roomService.getAllRooms(includeArchived);
            if (rooms.isEmpty()) {
                roomView.displayNoRoomsMessage();
            } else {
                for (Room room : rooms) {
                    roomView.displayRoom(room);
                }
            }
        } catch (ServiceException e) {
            adminView.displayErrorMessage(RED + "\t\t\tFailed to retrieve rooms: " + e.getMessage());
        }
    }
    
    public void displayAllRooms(boolean customerAccess) {
        try {
            List<Room> rooms = roomService.getAllRooms(customerAccess);
            if (rooms.isEmpty()) {
                roomView.displayNoRoomsMessage();
            } else {
                for (Room room : rooms) {
                    roomView.displayRoom(room);
                }
            }
        } catch (ServiceException e) {
            adminView.displayErrorMessage(RED + "\t\t\tFailed to retrieve rooms: " + e.getMessage());
        }
    }
    
    public void displayAllRoomsCustomer(boolean customerAccess) {
        try {
            List<Room> rooms = roomService.getAllRooms(customerAccess);
            if (rooms.isEmpty()) {
                roomView.displayNoRoomsMessage();
            } else {
                for (Room room : rooms) {
                    roomView.displayRoomCustomer(room);
                }
            }
        } catch (ServiceException e) {
            adminView.displayErrorMessage(RED + "\t\t\tFailed to retrieve rooms: " + e.getMessage());
        }
    }


    private void displayRoomById() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter room ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            if (!ValidationUtils.isNonNegative(id)) {
                roomView.displayInvalidRoomIdMessage();
                return;
            }

            Room room = roomService.getRoomById(id);
            if (room != null) {
                roomView.displayRoom(room);
            } else {
                roomView.displayRoomNotFoundMessage(id);
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid room ID.");
        }
    }

    private void addRoom() {
        try {
            Room newRoom = getRoomDetailsFromInput();
            if (newRoom == null) {
                adminView.displayErrorMessage(RED + "\t\t\tRoom creation failed due to invalid inputs.");
                return;
            }

            boolean added = roomService.addRoom(newRoom);
            if (added) {
                roomView.displayRoomAddedSuccess();
            } else {
                roomView.displayRoomAdditionFailed();
            }
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }

    private Room getRoomDetailsFromInput() {
        try {            
            System.out.print(YELLOW + "\t\t\t Enter room number: ");
            int number = sc.nextInt();
            sc.nextLine();

            System.out.print(YELLOW + "\t\t\t Enter room type: ");
            String type = sc.nextLine();

            System.out.print(YELLOW + "\t\t\t Enter room price: ");
            double price = sc.nextDouble();
            sc.nextLine();

            System.out.print(YELLOW + "\t\t\t Enter room capacity: ");
            int capacity = sc.nextInt();
            sc.nextLine();

            System.out.print(YELLOW + "\t\t\t Enter room description: ");
            String description = sc.nextLine();

            System.out.print(YELLOW + "\t\t\t Enter hotel ID: ");
            int hotelId = sc.nextInt();
            sc.nextLine();
            
            if (number <= 0 || price <= 0 || capacity <= 0 || hotelId <= 0 || type.isEmpty() || description.isEmpty()) {
                adminView.displayErrorMessage(RED + "\t\t\tInvalid data. Please fill all fields correctly.");
                return null;
            }

            Room newRoom = new Room();
            newRoom.setRoomNumber(number);
            newRoom.setRoomType(type);
            newRoom.setRoomPrice(price);
            newRoom.setRoomCapacity(capacity);
            newRoom.setRoomDescription(description);
            newRoom.setHotelId(hotelId);

            return newRoom;

        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter the correct data type.");
            return null;
        }
    }

    private void updateRoom() {
        try {
            System.out.print(YELLOW + "\t\t\t Enter room ID to update: ");
            int updateRoomId = sc.nextInt();
            sc.nextLine();

            Room roomToUpdate = roomService.getRoomById(updateRoomId);
            if (roomToUpdate == null) {
                roomView.displayRoomNotFoundMessage(updateRoomId);
                return;
            }

            
            System.out.print(YELLOW + "\t\t\t Enter new room number (leave blank to keep current): ");
            String newNumberInput = sc.nextLine();
            if (!newNumberInput.isEmpty()) {
                roomToUpdate.setRoomNumber(Integer.parseInt(newNumberInput));
            } else {
                roomToUpdate.setRoomNumber(roomToUpdate.getRoomNumber()); 
            }

            System.out.print(YELLOW + "\t\t\t Enter new room type (leave blank to keep current): ");
            String newType = sc.nextLine();
            if (!newType.isEmpty()) {
                roomToUpdate.setRoomType(newType);
            }

            System.out.print(YELLOW + "\t\t\t Enter new room price (leave blank to keep current): ");
            String newPriceInput = sc.nextLine();
            if (!newPriceInput.isEmpty()) {
                roomToUpdate.setRoomPrice(Double.parseDouble(newPriceInput));
            } else {
                roomToUpdate.setRoomPrice(roomToUpdate.getRoomPrice());
            }
            
            System.out.print(YELLOW + "\t\t\tEnter new room capacity (leave blank to keep current): ");
            String newCapacityInput = sc.nextLine();
            if (!newCapacityInput.isEmpty()) {
                roomToUpdate.setRoomCapacity(Integer.parseInt(newCapacityInput));
            } else {
                roomToUpdate.setRoomCapacity(roomToUpdate.getRoomCapacity()); 
            }

            System.out.print(YELLOW + "\t\t\tEnter new room description (leave blank to keep current): ");
            String newDescription = sc.nextLine();
            if (!newDescription.isEmpty()) {
                roomToUpdate.setRoomDescription(newDescription);
            } else {
                roomToUpdate.setRoomDescription(roomToUpdate.getRoomDescription());
            }
            
            String hotelName = roomService.fetchHotelName(roomToUpdate.getHotelId());
            if (hotelName != null) {
                roomToUpdate.setHotelName(hotelName);
            } else {
                adminView.displayErrorMessage(RED + "\t\t\tHotel name could not be found for hotel ID: " + roomToUpdate.getHotelId());
                return; 
            }
            
            System.out.println(GREEN + "\t\t\tUpdating Room with ID: " + roomToUpdate.getRoomId() + ", Hotel ID: " + roomToUpdate.getHotelId() + ", Hotel Name: " + roomToUpdate.getHotelName());

            boolean updated = roomService.updateRoom(roomToUpdate);
            if (updated) {
                roomView.displayRoomUpdatedSuccess();
            } else {
                roomView.displayRoomUpdateFailed();
            }
        } catch (InputMismatchException | NumberFormatException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please provide valid data.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }

    public void archiveRoom() {
        try {
            System.out.print(YELLOW + "\t\t\t Enter room ID to archive: ");
            int roomId = sc.nextInt();
            sc.nextLine();

            boolean archived = roomService.archiveRoom(roomId);
            if (archived) {
                roomView.displayRoomArchivedSuccess();
            } else {
                roomView.displayRoomArchiveFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid room ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }
    
    public void unarchiveRoom() {
        try {
            System.out.print(YELLOW + "\t\t\t Enter room ID to unarchive: ");
            int roomId = sc.nextInt();
            sc.nextLine();

            boolean archived = roomService.unarchiveRoom(roomId);
            if (archived) {
                roomView.displayRoomUnarchivedSuccess();
            } else {
                roomView.displayRoomUnarchiveFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid room ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }

    private void deleteRoom() {
        try {
            System.out.print(YELLOW + "\t\t\t Enter room ID to delete: ");
            int deleteRoomId = sc.nextInt();
            sc.nextLine();

            boolean deleted = roomService.deleteRoom(deleteRoomId);
            if (deleted) {
                roomView.displayRoomDeletedSuccess();
            } else {
                roomView.displayRoomDeletionFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid room ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }
}