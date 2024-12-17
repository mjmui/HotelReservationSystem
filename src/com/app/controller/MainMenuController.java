package com.app.controller;

import com.app.dao.impl.ReservationDAOImpl;
import com.app.dao.impl.RoomDAOImpl;
import com.app.view.MainMenuView;
import com.app.view.AdminView;
import com.app.service.CustomerService;
import com.app.service.ReservationService;
import com.app.service.RoomService;
import com.app.service.impl.CustomerServiceImpl;
import com.app.service.impl.ReservationServiceImpl;
import com.app.service.impl.RoomServiceImpl;
import static com.app.util.ConsoleUtils.RED;
import com.app.view.CustomerView;
import com.app.view.ReservationView;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenuController {
    private final AdminView adminView;
    private final MainMenuView mainMenuView;
    private final CustomerView customerView;
    private final ReservationView reservationView;
    private final CustomerService customerService;
    private final ReservationService reservationService;
    private final RoomService roomService;
    private final CustomerController customerController;
    private final AdminController adminController;
    private final ReservationController reservationController;
    private final RoomController roomController;
    private final Scanner sc;

    public MainMenuController() {
        this.sc = new Scanner(System.in);
        this.adminView = new AdminView();
        this.customerView = new CustomerView();
        this.mainMenuView = new MainMenuView();
        this.reservationView = new ReservationView();
        this.customerService = new CustomerServiceImpl();
        this.reservationService = new ReservationServiceImpl(new ReservationDAOImpl(), new RoomDAOImpl());
        this.roomService = new RoomServiceImpl();
        this.reservationController = new ReservationController(reservationService, adminView, sc, 
            mainMenuView, reservationView);
        this.roomController = new RoomController(roomService, adminView, mainMenuView, sc);
        this.customerController = new CustomerController(customerService, new ReservationDAOImpl(), 
            adminView, mainMenuView, customerView, reservationController, roomController, sc);
        this.adminController = new AdminController(mainMenuView, sc, adminView, reservationView, 
            customerService, reservationService, reservationController);
    }
    
  public static void main(String[] args) {
        MainMenuController mainMenu = new MainMenuController();
        mainMenu.displayMainMenu();
    }
    
    public void displayMainMenu() {
        boolean exit = false;
        mainMenuView.displayWelcomeMessage();
        
        while (!exit) {
            mainMenuView.displayMainOptions();
            try {
                int choice = sc.nextInt();
                sc.nextLine(); 
                
                switch (choice) {
                    case 1:
                        adminController.displayAdminMenu();
                        break;
                    case 2:
                        customerController.displayCustomerViewMenu();
                        break;
                    case 3:
                        exit = true;
                        adminView.displayExitMessage();
                        break;
                    default:
                        mainMenuView.displayInvalidOptionMessage();
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid option.");
            } catch (Exception e) {
                adminView.displayErrorMessage(RED + "\t\t\tAn unexpected error occurred: " + e.getMessage());
            }
        }
        
        sc.close();
    }
}