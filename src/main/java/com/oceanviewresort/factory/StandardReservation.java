package com.oceanviewresort.factory;

import java.time.LocalDate;

public class StandardReservation implements ReservationInterface {
     private int nights;
     
     private double roomRate;

     public StandardReservation(int nights, double roomRate){
        this.nights=nights;
        this.roomRate=6000;
     }

    @Override
    public double calculateTotalPrice(){
        
        return nights*roomRate;
    }

}
