package com.oceanviewresort.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.oceanviewresort.model.Reservation;

import com.oceanviewresort.util.DBConnection;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    private Connection connection;

    public ReservationDAO(){
      try{this.connection = DBConnection.getInstance();}
      catch(SQLException e){
        e.printStackTrace();
      }
    }

    public Reservation insertReservation(String guest_name, String guest_address, String contact_number, String room_type, String check_in, String check_out,double total_price){
        String sql = "INSERT INTO reservations (guest_name, guest_address, contact_number, room_type, check_in, check_out,total_price) VALUES (?, ?, ?, ?, ?, ?,?)";
        try(PreparedStatement stmt=connection.prepareStatement(
            sql,PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, guest_name);

            stmt.setString(2, guest_address);
             stmt.setString(3, contact_number);
              stmt.setString(4, room_type);
              stmt.setString(5, check_in);
              stmt.setString(6, check_out);
              stmt.setDouble(7, total_price);
              int affectedRows=stmt.executeUpdate();
              if(affectedRows>0){         
                try(ResultSet rs=stmt.getGeneratedKeys() ){    if(rs.next()){
                Reservation reservation=new Reservation(
                    guest_name, guest_address, contact_number, room_type, check_in, check_out,total_price);
                reservation.setReservation_id(rs.getInt(1));
               
                return reservation;

            }};
}

        

        }
         
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public List <Reservation> getAllReservations(){
        List<Reservation>reservations=new ArrayList<>();
        String sql = "SELECT * FROM reservations";
        try(PreparedStatement stmt=connection.prepareStatement(sql)){
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Reservation reservation=new Reservation(
                    rs.getString("guest_name"),
                    rs.getString("guest_address"),
                    rs.getString("contact_number"),
                    rs.getString("room_type"),
                    rs.getString("check_in"),
                    rs.getString("check_out"),
                    rs.getDouble("total_price")
                );
                reservation.setReservation_id(rs.getInt("reservation_id"));
                reservations.add(reservation);
            }
        }
        catch(Exception e){
            e.printStackTrace();}
            return reservations;}


         
        
          // ✅ Get a single reservation by ID
    public Reservation getReservationById(int reservationId){
        String sql = "SELECT * FROM reservations WHERE reservation_id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Reservation reservation = new Reservation(
                    rs.getString("guest_name"),
                    rs.getString("guest_address"),
                    rs.getString("contact_number"),
                    rs.getString("room_type"),
                    rs.getString("check_in"),
                    rs.getString("check_out"),
                    rs.getDouble("total_price")
                );
                reservation.setReservation_id(rs.getInt("reservation_id"));
                return reservation;
            }
        } catch(Exception e){ e.printStackTrace(); }

        return null;
    }
}



