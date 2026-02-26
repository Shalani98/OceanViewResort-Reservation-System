package com.oceanviewresort.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Statement;

import com.oceanviewresort.model.Bill;
import com.oceanviewresort.util.DBConnection;

public class BillDAO {
    private Connection connection;
    

    public BillDAO(){
      try{this.connection = DBConnection.getInstance();}
      catch(SQLException e){
        e.printStackTrace();
      }
    }

    public BillDAO(Connection connection) {
        this.connection = connection;
       
    }

      public Bill createBill(int reservationId) {
    String sql = "INSERT INTO bills (reservation_id) VALUES (?)";

    try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setInt(1, reservationId);
        stmt.executeUpdate();

        // ✅ fetch full bill from DB (including generated_date)
        return getBillByReservationId(reservationId);

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}

    public Bill getBillByReservationId(int reservationId) {
        String sql = "SELECT * FROM bills WHERE reservation_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setReservationId(reservationId);
                bill.setGeneratedDate(rs.getTimestamp("generated_date").toLocalDateTime());
                return bill;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}