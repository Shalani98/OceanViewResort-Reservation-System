package com.oceanviewresort.controller;

import com.oceanviewresort.model.Bill;
import com.oceanviewresort.model.BillRequest;
import com.oceanviewresort.service.BillService;
import com.oceanviewresort.dao.BillDAO;
import com.oceanviewresort.server.HttpServerMain;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import com.google.gson.Gson;

public class BillController {

    private final BillService billService;
     
    private final Gson gson = new Gson();

    
    
    // Constructor for dependency injection
    public BillController(BillService billService) {
        this.billService = billService;
    }
     
     
     


     public void handle(HttpExchange exchange) {
    String method = exchange.getRequestMethod();
    try {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BillRequest requestBody = gson.fromJson(isr, BillRequest.class);

        if (method.equalsIgnoreCase("POST")) {
            // Use reservation_id from JSON
            BillRequest response = billService.generateBill(requestBody.getReservationId());
            sendResponse(exchange, 200, gson.toJson(response));
        } else if (method.equalsIgnoreCase("GET")) {
            BillRequest response = billService.getBill(requestBody.getReservationId());
            sendResponse(exchange, 200, gson.toJson(response));
        } else {
            sendResponse(exchange, 405, "Method Not Allowed");
        }
    } catch (Exception e) {
        e.printStackTrace();
        try { sendResponse(exchange, 500, "Internal Server Error"); } catch (Exception ex) { ex.printStackTrace(); }
    }
}

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws Exception {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}