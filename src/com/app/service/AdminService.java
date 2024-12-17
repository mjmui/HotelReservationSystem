package com.app.service;

import com.app.model.Admin;
import java.util.List;

public interface AdminService {
    boolean addAdmin(String email, String password, String name);
    Admin getAdminById(int adminId);
    List<Admin> getAllAdmins();
    boolean updateAdmin(Admin admin);
    boolean archiveAdmin(int adminId);
    boolean deleteAdmin(int adminId);
    boolean login(String email, String password);
}
