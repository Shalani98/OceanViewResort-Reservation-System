package com.oceanviewresort.service;

import com.oceanviewresort.dao.BillDAO;
import com.oceanviewresort.dao.ReservationDAO;
import com.oceanviewresort.model.Bill;
import com.oceanviewresort.model.Reservation;
import com.oceanviewresort.model.BillRequest; // DTO

public class BillService {

    private BillDAO billDAO;
    private ReservationDAO reservationDAO;

    public BillService(BillDAO billDAO, ReservationDAO reservationDAO) {
        this.billDAO = billDAO;
        this.reservationDAO = reservationDAO;
    }

    // Generate a new bill
    public BillRequest generateBill(int reservationId) {

        // 1️⃣ Fetch reservation details
        Reservation reservation = reservationDAO.getReservationById(reservationId);
        if (reservation == null) {
            throw new RuntimeException("Reservation not found");
        }

        // 2️⃣ Create bill in DB
        Bill bill = billDAO.createBill(reservationId);

       

        // 4️⃣ Build response DTO
        BillRequest response = new BillRequest();
        response.setBillId(bill.getBillId());
        response.setGeneratedDate(bill.getGeneratedDate());
        response.setReservation(reservation);
    

        return response;
    }

    // Get an existing bill
    public BillRequest getBill(int reservationId) {
        Bill bill = billDAO.getBillByReservationId(reservationId);
        if (bill == null) {
            throw new RuntimeException("Bill not found");
        }

        Reservation reservation = reservationDAO.getReservationById(reservationId);
        

        BillRequest response = new BillRequest();
        response.setBillId(bill.getBillId());
        response.setGeneratedDate(bill.getGeneratedDate());
        response.setReservation(reservation);
        

        return response;
    }
}