package com.oceanviewresort.controller;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

import com.google.gson.Gson;
import com.oceanviewresort.model.Reservation;
import com.oceanviewresort.server.HttpServerMain;
import com.oceanviewresort.service.ReservationService;
public class ReservationController {

    private ReservationService reservationService;
    private final Gson gson = new Gson();

public ReservationController(){
reservationService=new ReservationService();
}

public void createReservation(HttpExchange exchange) throws IOException {

    try {
        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody());
        Reservation request = gson.fromJson(reader, Reservation.class);

        Reservation reservation = reservationService.createReservation(
            request.getGuest_name(),
            request.getGuest_address(),
            request.getContact_number(),
            request.getRoom_type(),
            request.getCheck_in(),
            request.getCheck_out()
        );

        if (reservation != null) {
            HttpServerMain.sendResponse(exchange, 200, "Reservation created successfully");
        } else {
            HttpServerMain.sendResponse(exchange, 500, "Failed to create reservation");
        }

    } catch (Exception e) {
        e.printStackTrace(); // 👉 prints error in terminal
        HttpServerMain.sendResponse(exchange, 500, "Error: " + e.getMessage()); // 👉 shows error in Swagger
    }
}
     

    public void getAllReservations(HttpExchange exchange) throws IOException {
    try {
        // Call service to get list of reservations
        List<Reservation> reservations = reservationService.getAllReservations();

        

        // Send JSON response
        HttpServerMain.sendResponse(exchange, 200,reservations);

    } catch (Exception e) {
        e.printStackTrace(); // prints error in terminal
        HttpServerMain.sendResponse(exchange, 500, "Error: " + e.getMessage());
    }
}

}