package com.app.service.impl;

import com.app.dao.AdminDAO;
import com.app.dao.impl.AdminDAOImpl;
import com.app.model.Admin;
import com.app.exception.DataAccessException;
import com.app.exception.ServiceException;
import com.app.service.AdminService;
import com.app.util.ValidationUtils;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    private final AdminDAO adminDAO;

    public AdminServiceImpl() {
            this.adminDAO = new AdminDAOImpl(); 
    }

    @Override
    public boolean addAdmin(String email, String password, String name) {
        if (!ValidationUtils.isValidEmail(email)) {
            throw new ServiceException("Invalid email format");
        }
        Admin admin = new Admin();
        admin.setAdminEmail(email);
        admin.setAdminPassword(password);
        admin.setAdminName(name);

        try {
            return adminDAO.addAdmin(admin);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to add admin", e);
        }
    }

    @Override
    public Admin getAdminById(int adminId) {
        try {
            Admin admin = adminDAO.getAdminById(adminId);
            if (admin == null) {
                throw new ServiceException("Admin with ID " + adminId + " not found");
            }
            return admin;
        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving admin with ID: " + adminId, e);
        }
    }

    @Override
    public List<Admin> getAllAdmins() {
        try {
            return adminDAO.getAllAdmins();
        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving all admins", e);
        }
    }

     @Override
    public boolean updateAdmin(Admin admin) {
        if (!ValidationUtils.isValidEmail(admin.getAdminEmail())) {
            throw new ServiceException("Invalid email format");
        }
        try {
            return adminDAO.updateAdmin(admin);
        } catch (DataAccessException e) {
            throw new ServiceException("Error updating admin", e);
        }
    }

    @Override
    public boolean archiveAdmin(int adminId) {
        try {
            return adminDAO.archiveAdmin(adminId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error archiving admin with ID " + adminId, e);
        }
    }

    @Override
    public boolean deleteAdmin(int adminId) {
        try {
            if (adminDAO.getAdminById(adminId) == null) {
                throw new ServiceException("Admin with ID " + adminId + " does not exist.");
            }
            return adminDAO.deleteAdmin(adminId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error deleting admin with ID: " + adminId, e);
        }
    }

    @Override
    public boolean login(String email, String password) {
        if (!ValidationUtils.isValidEmail(email)) {
            throw new ServiceException("Invalid email format");
        }
        try {
            return adminDAO.login(email, password);
        } catch (DataAccessException e) {
            throw new ServiceException("Login failed for email: " + email, e);
        }
    }
}

