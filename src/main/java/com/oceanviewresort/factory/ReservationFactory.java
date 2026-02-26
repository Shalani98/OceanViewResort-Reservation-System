package com.oceanviewresort.factory;
import java.time.LocalDate;
import  com.oceanviewresort.dao.RoomDAO;
import  com.oceanviewresort.model.Room;
public class  ReservationFactory {
    private static  RoomDAO roomDAO= new RoomDAO();
    static {
        roomDAO = new RoomDAO();
    }
    public static ReservationInterface createReservation(String roomType, LocalDate checkIn, LocalDate checkOut){
        int nights = (int) java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);


        // Fetch Rooms
        Room room =roomDAO.getRoomByType(roomType);
        if(room==null){
            return null;
        }

        double roomRate=room.getRoomRate();
        if(roomType.equalsIgnoreCase("Standard")){
            return new StandardReservation(nights,roomRate);
        }
        if(roomType.equalsIgnoreCase("Superior")){
            return new SuperiorReservation(nights,roomRate);
        }
        if(roomType.equalsIgnoreCase("Deluxe")){
            return new DeluxeReservation(nights,roomRate);
        }
        return null;
    }

}
