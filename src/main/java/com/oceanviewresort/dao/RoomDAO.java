package com.oceanviewresort.dao;

import com.oceanviewresort.model.Room;
import com.oceanviewresort.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    private Connection connection;

    public RoomDAO() {
        try {
            this.connection = DBConnection.getInstance();  // same standalone style
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get Room by room type
    public Room getRoomByType(String roomType) {
        String sql = "SELECT * FROM rooms WHERE room_type = ?";
        Room room = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, roomType);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                room = new Room(
                        rs.getInt("room_id"),
                        rs.getString("room_type"),
                        rs.getDouble("roomRate"),
                        rs.getString("status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return room;
    }



    public List<Room> getAllRooms() {
    List<Room> rooms = new ArrayList<>();
    String sql = "SELECT * FROM rooms";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            rooms.add(new Room(
                rs.getInt("room_id"),
                rs.getString("room_type"),
                rs.getDouble("roomRate"),
                rs.getString("status")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return rooms;
}
    // Update room status after booking
    public void updateRoomStatus(String roomType, String status) {
        String sql = "UPDATE rooms SET status = ? WHERE room_type = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, roomType);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}