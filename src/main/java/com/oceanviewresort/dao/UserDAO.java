package com.oceanviewresort.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.oceanviewresort.model.User;
import com.oceanviewresort.util.DBConnection;

public class UserDAO {

private Connection connection;

    public UserDAO(){
      try{this.connection = DBConnection.getInstance();}
      catch(SQLException e){
        e.printStackTrace();
      }
    }

    public User getUser(String username,String password,String role){
        System.out.println("DAO INPUT: [" + username + "|" + password + "|" + role + "]");
        String sql = "SELECT * FROM users WHERE username = ? AND  password=? AND role=?";
        try(PreparedStatement stmt=connection.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            ResultSet rs=stmt.executeQuery();

            if(rs.next()){
                User user=new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;

            }

        }
         
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }



     // New method: get all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    // Optional: get user by ID
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    // ✅ Add User
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getRole());
        stmt.executeUpdate();
    }

    // ✅ Update User
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username=?, password=?, role=? WHERE user_id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getRole());
        stmt.setInt(4, user.getUser_id());
        stmt.executeUpdate();
    }

    // ✅ Delete User
    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.executeUpdate();
    }

}

