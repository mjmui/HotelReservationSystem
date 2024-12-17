package com.app.dao.impl;

import com.app.dao.AdminDAO;
import com.app.model.Admin;
import com.app.exception.DataAccessException;
import com.app.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public boolean addAdmin(Admin admin) {
        String query = "INSERT INTO tbladmin (admin_name, admin_email, admin_password) VALUES (?, ?, ?)";
        
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setString(1, admin.getAdminName());
            prep.setString(2, admin.getAdminEmail());
            prep.setString(3, admin.getAdminPassword());
            return prep.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new DataAccessException("Error adding admin", e);
        }
    }

    @Override
    public Admin getAdminById(int adminId) {
        String query = "SELECT * FROM tbladmin WHERE admin_id = ?";
        
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setInt(1, adminId);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return mapToAdmin(result);
                }
            }
            
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching admin by ID: " + adminId, e);
        }
        
        return null;
    }

    @Override
    public List<Admin> getAllAdmins() {
        String query = "SELECT * FROM tbladmin";
        List<Admin> admins = new ArrayList<>();

        try (Connection con = DbConnection.getConnection();
             Statement state = con.createStatement();
             ResultSet result = state.executeQuery(query)) {
            
            while (result.next()) {
                admins.add(mapToAdmin(result));
            }
            
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching all admins", e);
        }
        
        return admins;
    }

    @Override
    public boolean updateAdmin(Admin admin) {
        String query = "UPDATE tbladmin SET admin_name = ?, admin_email = ?, admin_password = ? WHERE admin_id = ?";
        
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setString(1, admin.getAdminName());
            prep.setString(2, admin.getAdminEmail());
            prep.setString(3, admin.getAdminPassword());
            prep.setInt(4, admin.getAdminId());
            return prep.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new DataAccessException("Error updating admin by ID: " + admin.getAdminId(), e);
        }
    }

    @Override
    public boolean archiveAdmin(int adminId) {
        String query = "UPDATE tbladmin SET is_archived = true WHERE admin_id = ?";
        
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setInt(1, adminId);
            return prep.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new DataAccessException("Error archiving admin by ID: " + adminId, e);
        }
    }

    @Override
    public boolean deleteAdmin(int adminId) {
        String query = "DELETE FROM tbladmin WHERE admin_id = ?";
        
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setInt(1, adminId);
            return prep.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting admin by ID: " + adminId, e);
        }
    }

    @Override
    public boolean login(String email, String password) {
        String query = "SELECT admin_password FROM tbladmin WHERE admin_email = ?";
        
        try (Connection con = DbConnection.getConnection();
             PreparedStatement prep = con.prepareStatement(query)) {
            
            prep.setString(1, email);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    String storedPassword = result.getString("admin_password");
                    return storedPassword.equals(password);
                } else {
                    System.out.println("Admin with email " + email + " not found.");
                    return false;
                }
            }
            
        } catch (SQLException e) {
            throw new DataAccessException("Error during admin login", e);
        }
    }

    private Admin mapToAdmin(ResultSet result) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(result.getInt("admin_id"));
        admin.setAdminName(result.getString("admin_name"));
        admin.setAdminEmail(result.getString("admin_email"));
        admin.setAdminPassword(result.getString("admin_password"));
        return admin;
    }
}
