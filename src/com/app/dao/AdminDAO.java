package com.app.dao;

import com.app.model.Admin;
import java.util.List;

public interface AdminDAO {
    boolean addAdmin(Admin admin);
    Admin getAdminById(int adminId);
    List<Admin> getAllAdmins();
    boolean updateAdmin(Admin admin);
    boolean archiveAdmin(int adminId);
    boolean deleteAdmin(int adminId);
    boolean login(String email, String password);
   
}

