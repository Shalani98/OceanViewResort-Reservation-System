package com.oceanviewresort.controller;

import com.oceanviewresort.service.RoomService;
import com.oceanviewresort.model.Room;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class RoomController implements HttpHandler {

    private RoomService service = new RoomService();
    private Gson gson = new Gson();

    @Override
public void handle(HttpExchange exchange) throws IOException {
    if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
        String path = exchange.getRequestURI().getPath();
        // if path is exactly "/api/rooms" -> get all rooms
        if (path.equals("/api/rooms")) {
            String response = gson.toJson(service.getAllRooms());
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            // else treat as get by type
            String roomType = path.substring(path.lastIndexOf("/") + 1);
            Room room = service.getRoomByType(roomType);
            String response = gson.toJson(room);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    } else {
        exchange.sendResponseHeaders(405, -1);
        exchange.close();
    }
}
}