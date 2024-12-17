package com.app.controller;

import com.app.dao.ReservationDAO;
import com.app.exception.ServiceException;
import com.app.model.Customer;
import com.app.service.CustomerService;
import com.app.view.AdminView;
import com.app.view.CustomerView;
import com.app.view.MainMenuView;
import static com.app.util.ConsoleUtils.YELLOW;
import static com.app.util.ConsoleUtils.RED;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CustomerController {
    private final CustomerService customerService;
    private final AdminView adminView;
    private final MainMenuView mainMenuView;
    private final CustomerView customerView;
    private final ReservationController reservationController;
    private final RoomController roomController;
    private final Scanner sc;

    public CustomerController(CustomerService customerService, ReservationDAO reservationDAO, AdminView adminView,
                          MainMenuView mainMenuView, CustomerView customerView, ReservationController reservationController,
                          RoomController roomController, Scanner sc) {
            this.sc = sc;
            this.customerService = customerService;
            this.adminView = adminView;
            this.mainMenuView = mainMenuView;
            this.customerView = customerView;
            this.reservationController = reservationController;
            this.roomController = roomController;
    }

    public void displayCustomerMenu() {
        boolean back = false;     
        while (!back) {
            customerView.displayCustomerManagementOptions();
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        displayAllCustomers();
                        break;
                    case 2:
                        displayCustomerById();
                        break;
                    case 3:
                        addCustomer();
                        break;
                    case 4:
                        updateCustomer();
                        break;
                    case 5:
                        archiveCustomer();
                        break;
                    case 6:
                        unarchiveCustomer();
                        break;
                    case 7:
                        deleteCustomer();
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

    private void displayAllCustomers() {
        try {
            String includeArchivedInput;
            boolean includeArchived = false;

            while (true) {
                System.out.print(YELLOW + "\t\t\tWould you like to include archived customers? (yes/no): ");
                includeArchivedInput = sc.nextLine().trim().toLowerCase();

                if (includeArchivedInput.equals("yes")) {
                    includeArchived = true;
                    break;
                } else if (includeArchivedInput.equals("no")) {
                    includeArchived = false;
                    break;
                } else {
                    System.out.println(RED + "\t\t\tInvalid input. Please enter 'yes' or 'no'.");
                }
            }
            List<Customer> customers = customerService.getAllCustomers(includeArchived);
            if (customers.isEmpty()) {
                customerView.displayNoCustomersMessage();
            } else {
                for (Customer customer : customers) {
                    customerView.displayCustomer(customer);
                }
            }
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }



    private void displayCustomerById() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter customer ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            Customer customer = customerService.getCustomerById(id);
            if (customer != null) {
                customerView.displayCustomer(customer);
            } else {
                customerView.displayCustomerNotFoundMessage(id);
            }
        } catch (InputMismatchException e) {
            sc.nextLine(); // Clear invalid input
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid customer ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }

    private void addCustomer() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter customer name: ");
            String name = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter customer email: ");
            String email = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter customer phone: ");
            String phone = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter customer address: ");
            String address = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter password: ");
            String password = sc.nextLine();

            Customer newCustomer = new Customer();
            newCustomer.setCustomerName(name);
            newCustomer.setCustomerEmail(email);
            newCustomer.setCustomerPhone(phone);
            newCustomer.setCustomerAddress(address);
            newCustomer.setCustomerPassword(password);

            boolean added = customerService.addCustomer(newCustomer);
            if (added) {
                customerView.displayCustomerAddedSuccess();
            } else {
                customerView.displayCustomerAdditionFailed();
            }
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }

    private void updateCustomer() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter customer ID to update: ");
            int updateId = sc.nextInt();
            sc.nextLine();

            Customer customerToUpdate = customerService.getCustomerById(updateId);
            if (customerToUpdate == null) {
                customerView.displayCustomerNotFoundMessage(updateId);
                return;
            }

            System.out.print(YELLOW + "\t\t\tEnter new name (leave blank to keep current): ");
            String updatedName = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter new email (leave blank to keep current): ");
            String updatedEmail = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter new phone (leave blank to keep current): ");
            String updatedPhone = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter new address (leave blank to keep current): ");
            String updatedAddress = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter new password (leave blank to keep current): ");
            String updatedPassword = sc.nextLine();

            if (!updatedName.isEmpty()) customerToUpdate.setCustomerName(updatedName);
            if (!updatedEmail.isEmpty()) customerToUpdate.setCustomerEmail(updatedEmail);
            if (!updatedPhone.isEmpty()) customerToUpdate.setCustomerPhone(updatedPhone);
            if (!updatedAddress.isEmpty()) customerToUpdate.setCustomerAddress(updatedAddress);
            if (!updatedPassword.isEmpty()) customerToUpdate.setCustomerPassword(updatedPassword);

            boolean updated = customerService.updateCustomer(customerToUpdate);
            if (updated) {
                customerView.displayCustomerUpdatedSuccess();
            } else {
                customerView.displayCustomerUpdateFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine(); // Clear invalid input
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid customer ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }

    private void archiveCustomer() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter customer ID to archive: ");
            int archiveId = sc.nextInt();
            sc.nextLine();

            Customer customerToArchive = customerService.getCustomerById(archiveId);
            if (customerToArchive == null) {
                customerView.displayCustomerNotFoundMessage(archiveId);
            } else {
                boolean archived = customerService.archiveCustomer(archiveId);
                if (archived) {
                    customerView.displayCustomerArchivedSuccess();
                } else {
                    customerView.displayCustomerArchiveFailed();
                }
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid customer ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }
    
    private void unarchiveCustomer() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter customer ID to unarchive: ");
            int archiveId = sc.nextInt();
            sc.nextLine();

            Customer customerToArchive = customerService.getCustomerById(archiveId);
            if (customerToArchive == null) {
                customerView.displayCustomerNotFoundMessage(archiveId);
            } else {
                boolean unarchived = customerService.unarchiveCustomer(archiveId);
                if (unarchived) {
                    customerView.displayCustomerUnarchivedSuccess();
                } else {
                    customerView.displayCustomerUnarchiveFailed();
                }
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid customer ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }

    private void deleteCustomer() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter customer ID to delete: ");
            int deleteId = sc.nextInt();
            sc.nextLine();

            boolean deleted = customerService.deleteCustomer(deleteId);
            if (deleted) {
                customerView.displayCustomerDeletedSuccess();
            } else {
                customerView.displayCustomerDeletionFailed();
            }
        } catch (InputMismatchException e) {
            sc.nextLine();
            adminView.displayErrorMessage(RED + "\t\t\tInvalid input. Please enter a valid customer ID.");
        } catch (ServiceException e) {
            adminView.displayErrorMessage(e.getMessage());
        }
    }
    
    public void displayCustomerViewMenu() {
        boolean exit = false;
        while (!exit) {
            try {
                customerView.displayCustomerOptions();
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        handleRegister();
                        break;
                    case 2:
                        handleLogin();
                        break;
                    case 3:
                        exit = true;
                        break;
                    case 4:
                        mainMenuView.displayExitMessage();
                        System.exit(0);
                        break;
                    default:
                        customerView.displayInvalidOptionMessage();
                }
            } catch (InputMismatchException e) {
                System.out.println(RED + "\t\t\tInvalid input. Please enter a number.");
                sc.nextLine(); 
            }
        }
    }

    private void handleRegister() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter your full name: ");
            String fullName = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter your address: ");
            String address = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter your phone number: ");
            String phone = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter your email: ");
            String email = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter your password: ");
            String password = sc.nextLine();

            Customer customer = new Customer();
            customer.setCustomerName(fullName);
            customer.setCustomerAddress(address);
            customer.setCustomerPhone(phone);
            customer.setCustomerEmail(email);
            customer.setCustomerPassword(password);

            if (customerService.addCustomer(customer)) {
                customerView.displayRegistrationSuccessMessage();
                
                displayCustomerFunctionsMenu(customer);
            } else {
                customerView.displayRegistrationFailedMessage();
            }
        } catch (Exception e) {
            System.out.println(RED + "\t\t\tAn error occurred during registration: " + e.getMessage());
        }
    }

    private void handleLogin() {
        try {
            System.out.print(YELLOW + "\t\t\tEnter email: ");
            String email = sc.nextLine();
            System.out.print(YELLOW + "\t\t\tEnter password: ");
            String password = sc.nextLine();

            if (customerService.login(email, password)) {
                customerView.displayLoginSuccessMessage();
                Customer customer = customerService.getCustomerByEmail(email);
                displayCustomerFunctionsMenu(customer);
            } else {
                customerView.displayLoginFailureMessage();
            }
        } catch (Exception e) {
            System.out.println(RED + "\t\t\tAn error occurred during login: " + e.getMessage());
        }
    }

    private void displayCustomerFunctionsMenu(Customer customer) {
        boolean back = false;
        while (!back) {
            try {
                customerView.displayCustomerFunctionsOptions();
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        customerView.displayCustomerAccountDetails(customer);
                        break;
                    case 2:
                        displayReservationManagementMenu(customer);
                        break;
                    case 3:
                        back = true;
                        break;
                    case 4:
                        mainMenuView.displayExitMessage();
                        System.exit(0);
                        break;
                    default:
                        customerView.displayInvalidOptionMessage();
                }
            } catch (InputMismatchException e) {
                System.out.println(RED + "\t\t\tInvalid input. Please enter a number.");
                sc.nextLine();
            }
        }
    }

    private void displayReservationManagementMenu(Customer customer) {
        boolean back = false;
        while (!back) {
            try {
                customerView.displayReservationManagementOptions();
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        reservationController.displayCustomerReservationById(customer.getCustomerId());
                        break;
                    case 2:
                        roomController.displayAllRoomsCustomer(false);
                        reservationController.addReservationCustomer(customer.getCustomerId());
                        break;
                    case 3:
                        reservationController.displayCustomerReservationById(customer.getCustomerId());
                        reservationController.cancelReservationCustomer(customer.getCustomerId());
                        break;
                    case 4:
                        back = true;
                        break;
                    case 5:
                        mainMenuView.displayExitMessage();
                        System.exit(0);
                        break;
                    default:
                        System.out.println(RED + "\t\t\tInvalid Choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println(RED + "\t\t\tInvalid input. Please enter a number.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println(RED + "\t\t\tAn error occurred: " + e.getMessage());
            }
        }
    }
}