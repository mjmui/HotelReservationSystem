/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.service;

import com.app.model.Customer;
import java.util.List;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    Customer getCustomerById(int customerId);
    Customer getCustomerByEmail(String email);
    Customer getCustomerByPhone(String phone);
    List<Customer> getAllCustomers(boolean includeArchived);
    boolean updateCustomer(Customer customer);
    boolean archiveCustomer(int customerId);
    boolean unarchiveCustomer(int customerId);
    boolean deleteCustomer(int customerId);
    boolean login(String email, String password); 
    boolean emailExists(String email);
}

