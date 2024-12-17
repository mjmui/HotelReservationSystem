package com.app.service.impl;

import com.app.dao.CustomerDAO;
import com.app.dao.impl.CustomerDAOImpl;
import com.app.exception.DataAccessException;
import com.app.exception.ServiceException;
import com.app.model.Customer;
import com.app.service.CustomerService;
import com.app.util.ValidationUtils;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerServiceImpl() {
            this.customerDAO = new CustomerDAOImpl(); 
    }

    
    @Override
    public boolean addCustomer(Customer customer) {
        
        if (customer.getCustomerName() == null || customer.getCustomerName().isEmpty()) {
            throw new ServiceException("Customer name is required.");
        }

        if (customer.getCustomerEmail() == null || !ValidationUtils.isValidEmail(customer.getCustomerEmail())) {
            throw new ServiceException("Valid email is required.");
        }
        
        if (customer.getCustomerAddress() == null){
            throw new ServiceException("Valid address is required.");
        }

        if (customer.getCustomerPhone() == null || !ValidationUtils.isValidPhone(customer.getCustomerPhone())) {
            throw new ServiceException("Valid phone number is required.");
        }

        if (customer.getCustomerPassword() == null || !ValidationUtils.isPasswordLong(customer.getCustomerPassword())) {
            throw new ServiceException("Password must be at least 6 characters long.");
        }

        if (customerDAO.emailExists(customer.getCustomerEmail())) {
            throw new ServiceException("Email already exists.");
        }

        try {
            return customerDAO.addCustomer(customer);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to add customer", e);
        }
    }

    @Override
    public Customer getCustomerById(int customerId) {
        try {
            return customerDAO.getCustomerById(customerId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error fetching customer by ID", e);
        }
    }
    
    @Override
    public Customer getCustomerByEmail(String email) {
        try {
            return customerDAO.getCustomerByEmail(email);
        } catch (DataAccessException e) {
            throw new ServiceException("Error fetching customer by email", e);
        }
    }

    @Override
    public List<Customer> getAllCustomers(boolean includeArchived) {
        try {
            return customerDAO.getAllCustomers(includeArchived);
        } catch (Exception e) {
            throw new ServiceException("Error fetching all customers", e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        if (customer == null || customer.getCustomerId() <= 0) {
            throw new ServiceException("Valid customer is required.");
        }

        if (customer.getCustomerName() == null || customer.getCustomerName().isEmpty()) {
            throw new ServiceException("Customer name is required.");
        }

        if (customer.getCustomerEmail() == null || !ValidationUtils.isValidEmail(customer.getCustomerEmail())) {
            throw new ServiceException("Valid email is required.");
        }

        if (customer.getCustomerPhone() == null || !ValidationUtils.isValidPhone(customer.getCustomerPhone())) {
            throw new ServiceException("Valid phone number is required.");
        }

        if (customer.getCustomerPassword() == null || !ValidationUtils.isPasswordLong(customer.getCustomerPassword())) {
            throw new ServiceException("Password must be at least 6 characters long.");
        }

        return customerDAO.updateCustomer(customer);
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        try {
            return customerDAO.deleteCustomer(customerId);
        } catch (Exception e) {
            throw new ServiceException("Error deleting customer by ID", e);
        }
    }

    @Override
    public boolean login(String email, String password) {
        if (email == null || !ValidationUtils.isValidEmail(email)) {
            throw new ServiceException("Valid email is required.");
        }
        if (password == null || password.isEmpty()) {
            throw new ServiceException("Password cannot be empty.");
        }

        Customer customer = customerDAO.getCustomerByEmail(email);
        if (customer == null || !customer.getCustomerPassword().equals(password)) {
            throw new ServiceException("Invalid email or password.");
        }
        return customerDAO.login(email, password);
    }

    @Override
    public boolean archiveCustomer(int customerId) {
        try {
            return customerDAO.archiveCustomer(customerId);
        } catch (Exception e) {
            throw new ServiceException("Error archiving customer", e);
        }
    }
    
    @Override
    public boolean unarchiveCustomer(int customerId) {
        try {
            return customerDAO.unarchiveCustomer(customerId);
        } catch (Exception e) {
            throw new ServiceException("Error unarchiving customer", e);
        }
    }


    @Override
    public boolean emailExists(String email) {
        try {
            return customerDAO.emailExists(email);
        } catch (Exception e) {
            throw new ServiceException("Error checking email existence", e);
        }
    }

    @Override
    public Customer getCustomerByPhone(String phone) {
        try {
            return customerDAO.getCustomerByPhone(phone);
        } catch (Exception e) {
            throw new ServiceException("Error fetching customer by phone number", e);
        }
    }
}
