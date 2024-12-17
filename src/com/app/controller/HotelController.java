package com.app.controller;

import com.app.exception.ServiceException;
import com.app.model.Hotel;
import com.app.service.HotelService;
import com.app.view.AdminView;
import com.app.view.MainMenuView;
import com.app.view.HotelView;
import static com.app.util.ConsoleUtils.YELLOW;
import static com.app.util.ConsoleUtils.RED;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class HotelController {

    private final HotelService hotelService;
    private final AdminView adminView;
    private final MainMenuView mainMenuView;
    private final HotelView hotelView;
    private final Scanner sc;

    public HotelController(HotelService hotelService, AdminView adminView, MainMenuView mainMenuView, Scanner sc) {
        this.hotelService = hotelService;
        this.adminView = adminView;
        this.mainMenuView = mainMenuView;
        this.hotelView = new HotelView();
        this.sc = sc;
    }

    public void displayHotelMenu() {
        boolean back = false;
        while (!back) {
            hotelView.displayHotelManagementOptions();
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        displayAllHotels();
                        break;
                    case 2:
                        displayHotelById();
                        break;
                    case 3:
                        addHotel();
                        break;
                    case 4:
                        updateHotel();
                        break;
                    case 5:
                        archiveHotel();
                        break;
                    case 6:
                        unarchiveHotel();
                        break;
                    case 7:
                        deleteHotel();
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
            }
        }
    }

    private void displayAllHotels() {
    try {
        System.out.print(YELLOW + "\t\t\tWould you like to include archived hotels? (yes/no): ");
        String includeArchivedInput = sc.nextLine().trim().toLowerCase();
        boolean includeArchived = includeArchivedInput.equals("yes");

        List<Hotel> hotels = hotelService.getAllHotels(includeArchived);
        
        if (hotels.isEmpty()) {
            hotelView.displayNoHotelsMessage();
        } else {
            for (Hotel hotel : hotels) {
                hotelView.displayHotel(hotel);
            }
        }
    } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }


    private void displayHotelById() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter hotel ID: ");
            int hotelId = sc.nextInt();
            sc.nextLine();

            Hotel hotel = hotelService.getHotelById(hotelId);
            if (hotel != null) {
                hotelView.displayHotel(hotel);
            } else {
                hotelView.displayHotelNotFoundMessage(hotelId);
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid hotel ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }

    private void addHotel() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter hotel name: ");
            String name = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter hotel location: ");
            String location = sc.nextLine();

            Hotel newHotel = new Hotel();
            newHotel.setHotelName(name);
            newHotel.setHotelLocation(location);

            boolean added = hotelService.addHotel(newHotel);
            if (added) {
                hotelView.displayHotelAddedSuccess();
            } else {
                hotelView.displayHotelAdditionFailed();
            }
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }

    private void updateHotel() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter hotel ID to update: ");
            int updateHotelId = sc.nextInt();
            sc.nextLine();

            Hotel hotelToUpdate = hotelService.getHotelById(updateHotelId);
            if (hotelToUpdate == null) {
                hotelView.displayHotelNotFoundMessage(updateHotelId);
                return;
            }

            System.out.print(YELLOW + "\t\t\tEnter new hotel name (leave blank to keep current): ");
            String newHotelName = sc.nextLine();
            if (!newHotelName.isEmpty()) {
                hotelToUpdate.setHotelName(newHotelName);
            }

            System.out.print(YELLOW + "\t\t\tEnter new hotel location (leave blank to keep current): ");
            String newHotelLocation = sc.nextLine();
            if (!newHotelLocation.isEmpty()) {
                hotelToUpdate.setHotelLocation(newHotelLocation);
            }

            boolean updated = hotelService.updateHotel(hotelToUpdate);
            if (updated) {
                hotelView.displayHotelUpdatedSuccess();
            } else {
                hotelView.displayHotelUpdateFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter valid data.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }
    
    private void archiveHotel() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter hotel ID to archive: ");
            int hotelId = sc.nextInt();
            sc.nextLine();

            boolean archived = hotelService.archiveHotel(hotelId);
            if (archived) {
                hotelView.displayHotelArchivedSuccess();
            } else {
                hotelView.displayHotelArchiveFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid hotel ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }
    
    private void unarchiveHotel() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter hotel ID to unarchive: ");
            int hotelId = sc.nextInt();
            sc.nextLine();

            boolean archived = hotelService.unarchiveHotel(hotelId);
            if (archived) {
                hotelView.displayHotelUnarchiveSuccess();
            } else {
                hotelView.displayHotelUnarchiveFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine(); 
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid hotel ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }


    private void deleteHotel() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter hotel ID to delete: ");
            int deleteHotelId = sc.nextInt();
            sc.nextLine();

            boolean deleted = hotelService.deleteHotel(deleteHotelId);
            if (deleted) {
                hotelView.displayHotelDeletedSuccess();
            } else {
                hotelView.displayHotelDeletionFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid hotel ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }
}