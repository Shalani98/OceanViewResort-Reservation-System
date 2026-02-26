package com.oceanviewresort.service;

import com.oceanviewresort.model.User;
import com.oceanviewresort.dao.UserDAO;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAllUsers() throws Exception {
        return userDAO.getAllUsers();
    }
     
    public void addUser(User user) throws SQLException {
        userDAO.addUser(user);
    }
    public void updateUser(User user) throws SQLException {
        userDAO.updateUser(user);
    }
    public void deleteUser(int userId) throws SQLException {
        userDAO.deleteUser(userId);
    }
     
}