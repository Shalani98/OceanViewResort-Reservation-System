package com.oceanviewresort.model;

public class UserResponse {
    private int user_id;
    private String username;
    private String role;

    public UserResponse(int user_id, String username, String role) {
        this.user_id = user_id;
        this.username = username;
        this.role = role;
    }

    public int getUser_id() { return user_id; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
}