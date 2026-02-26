package com.oceanviewresort.model;
import java.time.LocalDateTime;


public class Bill {
   private int billId;
    private int reservationId;
    private LocalDateTime generatedDate;

    public Bill() {}

    public Bill(int billId,int reservationId,LocalDateTime generatedDate){
        this.billId = billId;
        this.reservationId = reservationId;
        this.generatedDate = generatedDate;
    }
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public LocalDateTime  getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(LocalDateTime generatedDate) { this.generatedDate = generatedDate; }

    @Override
    public String toString() {
        return "Bill ID: " + billId + 
               "\nReservation ID: " + reservationId + 
               "\nGenerated Date: " + generatedDate;
    }
}
