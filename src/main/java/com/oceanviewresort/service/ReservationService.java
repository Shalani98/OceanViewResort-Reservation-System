package com.oceanviewresort.service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.oceanviewresort.dao.ReservationDAO;
import com.oceanviewresort.model.Bill;
import com.oceanviewresort.model.Reservation;
import com.oceanviewresort.model.Room;
import com.oceanviewresort.service.RoomService;

import com.oceanviewresort.factory.ReservationFactory;
import com.oceanviewresort.factory.ReservationInterface;

public class ReservationService {
  private ReservationDAO reservationDAO;
  
  private RoomService roomService=new RoomService();


  public ReservationService(){
    this.reservationDAO=new ReservationDAO();
  }
 
    
  
  public Reservation  createReservation( String guest_name,String guest_address, String contact_number,String room_type,String check_in,String check_out){
      
         //  Check if room is already occupied
        Room room = roomService.getRoomByType(room_type);
        if (room == null) {
            throw new RuntimeException("Room type not found");
        }
        if ("Occupied".equalsIgnoreCase(room.getStatus())) {
            throw new RuntimeException("Room is already occupied");
        }
      Reservation reservation=new Reservation();
        reservation.setGuest_name(guest_name);
        reservation.setGuest_address(guest_address);
        reservation.setContact_number(contact_number);
        reservation.setCheck_in(check_in);
        reservation.setCheck_out(check_out);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkInDate = LocalDate.parse(check_in, formatter);
        LocalDate checkOutDate = LocalDate.parse(check_out, formatter);
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        if (nights <= 0) {
            throw new IllegalArgumentException("Check-out date must be after check-in date.");
        }


        //price making using factory pattern
    ReservationInterface newReservation= ReservationFactory.createReservation(room_type, checkInDate, checkOutDate);
                double total_price=newReservation.calculateTotalPrice();
                        reservation.setTotal_price(total_price);
          if (newReservation == null) {
          throw new RuntimeException("Invalid room type");
}
         reservation=reservationDAO.insertReservation(guest_name, guest_address, contact_number, room_type, check_in, check_out,total_price);
          if (reservation != null) {
            // Update room status to "Occupied"
            roomService.updateRoomStatus(room_type, "Occupied");
        }


        return reservation;
          

  }

        public List<Reservation>getAllReservations(){
          return reservationDAO.getAllReservations();
          }

}
