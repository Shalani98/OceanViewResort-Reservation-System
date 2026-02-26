
package com.oceanviewresort.factory;

import java.time.LocalDate;

public class DeluxeReservation implements ReservationInterface {
    private int nights;
    
    private double roomRate;


    public DeluxeReservation(int nights,double roomRate){
        this.nights=nights;
        this.roomRate=12500;
    }

    @Override
    public double calculateTotalPrice(){
        
        return nights*roomRate;
    }

}


