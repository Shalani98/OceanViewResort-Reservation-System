package com.oceanviewresort.model;



public class Room {
    private int roomId;
    private String roomType;
    private double roomRate;
    private String status;

    // Constructors
    public Room() {}
    public Room(int roomId, String roomType, double roomRate, String status) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomRate = roomRate;
        this.status = status;
    }

    // Getters and Setters
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public double getRoomRate() { return roomRate; }
    public void setRoomRate(double roomRate) { this.roomRate = roomRate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
