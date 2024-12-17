package com.app.controller;

import com.app.dao.ReservationDAO;
import com.app.dao.impl.ReservationDAOImpl;
import com.app.exception.ServiceException;
import com.app.service.*;
import com.app.service.impl.*;
import static com.app.util.ConsoleUtils.RED;
import com.app.view.AdminView;
import com.app.view.CustomerView;
import com.app.view.MainMenuView;
import com.app.view.ReservationView;
import static com.app.util.ConsoleUtils.YELLOW;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminController {
    private final AdminView adminView;
    private final ReservationView reservationView;
    private final AdminService adminService;
    private final CustomerService customerService;
    private final ReservationService reservationService;
    private final CustomerView customerView;
    private final MainMenuView mainMenuView;
    private final ReservationController reservationController;
    private final CustomerController customerController;
    private final RoomController roomController;
    private final HotelController hotelController;
    private final ReservationDAO reservationDAO;
    private final RoomService roomService;
    private final HotelService hotelService;
    private final Scanner sc;

    public AdminController(MainMenuView mainMenuView, Scanner sc, AdminView adminView,
                           ReservationView reservationView, CustomerService customerService,
                           ReservationService reservationService, ReservationController reservationController) {
        this.sc = sc;
        this.adminView = adminView;
        this.reservationView = reservationView;
        this.customerView = new CustomerView();
        this.adminService = new AdminServiceImpl();
        this.hotelService = new HotelServiceImpl();
        this.customerService = customerService;
        this.reservationService = reservationService;
        this.mainMenuView = mainMenuView;
        this.reservationDAO = new ReservationDAOImpl();
        this.roomService = new RoomServiceImpl();
        this.hotelController = new HotelController(hotelService, adminView, mainMenuView, sc);
        this.reservationController = reservationController;
        this.roomController = new RoomController(roomService, adminView, mainMenuView, sc);
        this.customerController = new CustomerController(customerService, reservationDAO, adminView,
                          mainMenuView, customerView, reservationController, roomController,
                          sc);
        
    }

    public void displayAdminMenu() {
        boolean exit = false;
        while (!exit) {
            adminView.displayAdminOptions();
            try {
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        handleLogin();
                        break;
                    case 2:
                        exit = true;
                        break;
                    case 3:
                        adminView.displayExitMessage();
                        System.exit(0);
                        break;
                    default:
                        adminView.displayInvalidOptionMessage();
                }
            } catch (InputMismatchException e) {
                sc.nextLine(); 
                adminView.displayErrorMessage(RED + "\t\t\t Invalid input. Please enter a valid option.");
            } catch (Exception e) {
                adminView.displayErrorMessage(RED + "\t\t\t An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void handleLogin() {
        try {
            System.out.println("");
            System.out.print(YELLOW + "\t\t\t\tEnter email: ");
            String email = sc.nextLine();
            System.out.print(YELLOW + "\t\t\t\tEnter password: ");
            String password = sc.nextLine();

            if (adminService.login(email, password)) {
                adminView.displayLoginSuccessMessage();
                displayAdminFunctionsMenu();
            } else {
                adminView.displayLoginFailureMessage();
            }
        } catch (ServiceException e) {
            adminView.displayErrorMessage(RED + "\t\t\t\tLogin failed: " + e.getMessage());
        }
    }

    private void displayAdminFunctionsMenu() {
        boolean back = false;
        while (!back) {
            adminView.displayMainOptions();
            try {
                int choice = sc.nextInt();
                sc.nextLine(); 

                switch (choice) {
                    case 1:
                        customerController.displayCustomerMenu();
                        break;
                    case 2:
                        roomController.displayRoomMenu();
                        break;
                    case 3:
                        hotelController.displayHotelMenu();
                        break;
                    case 4:
                        reservationController.displayReservationMenu();
                        break;
                    case 5:
                        back = true;
                        break;
                    case 6:
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
}