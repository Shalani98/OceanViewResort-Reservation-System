package com.oceanviewresort.model;
import java.time.LocalDateTime;
public class BillRequest {
   
    private int billId;
    private int reservationId;
    private LocalDateTime generatedDate;
    private Reservation reservation;
    private double totalPrice;

    public BillRequest() {}

    public BillRequest(int billId, LocalDateTime generatedDate, Reservation reservation, double totalPrice) {
        this.billId = billId;
        this.generatedDate = generatedDate;
        this.reservation = reservation;
        this.totalPrice = totalPrice;
    }   
    public int getBillId(){
        return billId;
    }
    public void setBillId(int billId){
        this.billId = billId;
    }   
    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }
    public void setGeneratedDate(LocalDateTime generatedDate) {
        this.generatedDate = generatedDate;
    }
    public Reservation getReservation() {
        return reservation;
    }
    public void setReservation(Reservation reservation){
        this.reservation=reservation;
    }

}
