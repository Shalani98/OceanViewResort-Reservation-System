package com.oceanviewresort.factory;

import java.time.LocalDate;

public class SuperiorReservation implements ReservationInterface {
    private int nights;
    private double roomRate;
    


    public SuperiorReservation(int nights,double roomRate){
        this.nights=nights;
        this.roomRate=9000;
    }

    @Override
    public double calculateTotalPrice(){
       
        return nights*roomRate;
    }

}
