package com.app.dao;

import com.app.model.Customer;
import com.app.model.Reservation;
import java.util.List;

public interface CustomerDAO {
    boolean addCustomer(Customer customer);
    Customer getCustomerById(int customerId);
    Customer getCustomerByEmail(String email);
    Customer getCustomerByPhone(String phone);
    List<Reservation> getReservationsByCustomerId(int customerId);
    List<Customer> getAllCustomers(boolean isArchived);
    boolean updateCustomer(Customer customer);
    boolean archiveCustomer(int customerId);
    boolean unarchiveCustomer(int customerId);
    boolean deleteCustomer(int customerId);
    boolean emailExists(String email);
    boolean login(String email, String password);
}
