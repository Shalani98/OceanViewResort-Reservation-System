package com.oceanviewresort.service;



import java.util.List;

import com.oceanviewresort.dao.RoomDAO;
import com.oceanviewresort.model.Room;

public class RoomService {

    private RoomDAO roomDAO = new RoomDAO();
    public List<Room> getAllRooms() {
    return roomDAO.getAllRooms();
}
    public Room getRoomByType(String roomType) {
        return roomDAO.getRoomByType(roomType);
    }

    public void updateRoomStatus(String roomType, String status) {
        roomDAO.updateRoomStatus(roomType, status);
    }
}
