package com.oceanviewresort.factory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public interface ReservationInterface {

    double calculateTotalPrice();

    default int getNights(LocalDate checkIn, LocalDate checkOut) {
        return (int) ChronoUnit.DAYS.between(checkIn, checkOut);
    }

}
