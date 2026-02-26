package com.oceanviewresort.controller;

import com.oceanviewresort.model.User;
import com.oceanviewresort.model.UserResponse;
import com.oceanviewresort.service.UserService;
import com.oceanviewresort.server.HttpServerMain;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class UserController {

    private final UserService userService;
    private final Gson gson = new Gson();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ GET ALL USERS
    public void handle(HttpExchange exchange) throws IOException {
        try {
            List<User> users = userService.getAllUsers();

            // Remove password from response
            List<UserResponse> responseList = users.stream()
                    .map(u -> new UserResponse(
                            u.getUser_id(),
                            u.getUsername(),
                            u.getRole()))
                    .collect(Collectors.toList());

            HttpServerMain.sendResponse(exchange, 200, responseList);

        } catch (Exception e) {
            e.printStackTrace();
            HttpServerMain.sendResponse(exchange, 500, "Error fetching users");
        }
    }

    // ✅ ADD USER (POST)
    public void addUser(HttpExchange exchange) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody()));

            User user = gson.fromJson(reader, User.class);
            

            userService.addUser(user);

            HttpServerMain.sendResponse(exchange, 200, "User added successfully");

        } catch (Exception e) {
            e.printStackTrace();
            HttpServerMain.sendResponse(exchange, 500, "Error adding user: " + e.getMessage());
        }
    }

    // ✅ UPDATE USER (PUT)
    public void updateUser(HttpExchange exchange) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody()));

            User user = gson.fromJson(reader, User.class);

            userService.updateUser(user);

            HttpServerMain.sendResponse(exchange, 200, "User updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
            HttpServerMain.sendResponse(exchange, 500, "Error updating user: " + e.getMessage());
        }
    }

    // ✅ DELETE USER (DELETE)
   public void deleteUser(HttpExchange exchange) throws IOException {
    try {
        String query = exchange.getRequestURI().getQuery();
        if (query == null) {
            HttpServerMain.sendResponse(exchange, 400, "Missing user_id");
            return;
        }

        int userId = -1;
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyVal = pair.split("=");
            if (keyVal[0].equals("user_id") && keyVal.length > 1) {
                userId = Integer.parseInt(keyVal[1]);
                break;
            }
        }

        if (userId == -1) {
            HttpServerMain.sendResponse(exchange, 400, "Missing user_id");
            return;
        }

        userService.deleteUser(userId);
        HttpServerMain.sendResponse(exchange, 200, "User deleted successfully");

    } catch (Exception e) {
        e.printStackTrace();
        HttpServerMain.sendResponse(exchange, 500, "Error deleting user: " + e.getMessage());
    }
}
}