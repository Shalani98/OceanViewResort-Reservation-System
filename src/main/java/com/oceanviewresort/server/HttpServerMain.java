package com.oceanviewresort.server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.sql.SQLException;
import com.oceanviewresort.util.DBConnection;  // ← ADD THIS
import com.oceanviewresort.controller.BillController;
import com.oceanviewresort.controller.LoginController;
import com.oceanviewresort.controller.ReportController;
import com.oceanviewresort.controller.ReservationController;
import com.oceanviewresort.controller.RoomController;
import com.oceanviewresort.controller.UserController;
import com.oceanviewresort.service.UserService;
import com.oceanviewresort.service.BillService;
import com.oceanviewresort.dao.UserDAO;
import com.oceanviewresort.dao.BillDAO;

import com.oceanviewresort.dao.ReservationDAO;
import com.oceanviewresort.model.Bill;
import com.oceanviewresort.model.User;

public class HttpServerMain {

    private static final Gson gson = new Gson();

    public static void main(String[] args) throws IOException {

        //DAOs
        UserDAO userDAO = new UserDAO();
        BillDAO billDAO = new BillDAO();
        ReservationDAO reservationDAO = new ReservationDAO();

        //Services
        UserService userService = new UserService(userDAO);
        BillService billService=new BillService(billDAO,reservationDAO);
        
        // Initialize controllers (tests DB connection immediately)
        LoginController loginController = new LoginController();
        UserController userController = new UserController(userService);
        ReservationController reservationController = new ReservationController();
        BillController billController = new BillController(billService);
        RoomController roomController = new RoomController();
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // ----------------- API Endpoints -----------------
        server.createContext("/api", exchange -> {
            
               if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                addCORS(exchange);
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            }
            addCORS(exchange);

            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();
            switch (path) {
                case "/api/login":
                    if (method.equalsIgnoreCase("POST")) loginController.handleLogin(exchange);
                    else send405(exchange);
                    break;

                case "/api/users":
                    userController.handle(exchange);
                    send405(exchange); // Only use sub-endpoints
                    break;
                case "/api/users/add":
                    if (method.equalsIgnoreCase("POST")) userController.addUser(exchange);
                    else send405(exchange);
                    break;
                case "/api/users/update":
                    if (method.equalsIgnoreCase("PUT")) userController.updateUser(exchange);
                    else send405(exchange);
                    break;
                case "/api/users/delete":
                    if (method.equalsIgnoreCase("DELETE")) userController.deleteUser(exchange);
                    else send405(exchange);
                    break;
                case "/api/reservation":
                     if (method.equalsIgnoreCase("POST")) {
                    // Create a new reservation
                     reservationController.createReservation(exchange);
                     } else if (method.equalsIgnoreCase("GET")) {
                      // Get all reservations
                      reservationController.getAllReservations(exchange);
                     } else {
                     send405(exchange);
                     }
                     break;
                case "/api/bill":
                    if (method.equalsIgnoreCase("POST")) billController.handle(exchange);
                    else send405(exchange);
                    break;
                case "/api/rooms":
                    if (method.equalsIgnoreCase("GET")) roomController.handle(exchange);
                    else send405(exchange);
                    break;    
                case "/api/test-db":  // ← YOUR PERFECT ADDITION
                    if (method.equalsIgnoreCase("GET")) {
                        testDatabaseConnection(exchange);
                    } else send405(exchange);
                    break;
                default:
                    addCORS(exchange);
                    exchange.sendResponseHeaders(404, -1);
            }
            exchange.close();
        });

        // ----------------- Swagger UI -----------------
        server.createContext("/swagger", exchange -> {
            // Handle preflight OPTIONS for Swagger
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                addCORS(exchange);
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            }

            addCORS(exchange);
            String uriPath = exchange.getRequestURI().getPath();
            String relativePath = uriPath.replaceFirst("/swagger", "");
            if (relativePath.isEmpty() || relativePath.equals("/")) {
                relativePath = "/index.html";
            }

            File file = new File("src/main/webapp/swagger/dist" + relativePath);
            if (!file.exists() || file.isDirectory()) {
                String response = "File Not Found: " + relativePath;
                exchange.sendResponseHeaders(404, response.length());
                exchange.getResponseHeaders().add("Content-Type", "text/plain");
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
                return;
            }

            String contentType = getContentType(relativePath);
            exchange.getResponseHeaders().add("Content-Type", contentType);
            byte[] bytes = Files.readAllBytes(file.toPath());
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
        });

        // ----------------- OpenAPI JSON -----------------
        server.createContext("/openapi.json", exchange -> {
            addCORS(exchange);
            File file = new File("src/main/webapp/swagger/openapi.json");
            if (!file.exists()) {
                String response = "File Not Found: openapi.json";
                exchange.sendResponseHeaders(404, response.length());
                exchange.getResponseHeaders().add("Content-Type", "text/plain");
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
                return;
            }

            byte[] bytes = Files.readAllBytes(file.toPath());
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
        });

        // ----------------- Favicons -----------------
        server.createContext("/favicon-32x32.png", exchange -> {
            serveStaticFile(exchange, "src/main/webapp/swagger/dist/favicon-32x32.png", "image/png");
        });
        server.createContext("/favicon-16x16.png", exchange -> {
            serveStaticFile(exchange, "src/main/webapp/swagger/dist/favicon-16x16.png", "image/png");
        });

        // ----------------- Start Server -----------------
        server.setExecutor(null);
        server.start();
        System.out.println("🌊 Server running at http://localhost:8080/api");
        System.out.println("📊 Swagger docs at http://localhost:8080/swagger");
        System.out.println("🔍 Test DB: http://localhost:8080/api/test-db");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.stop(0);
            System.out.println("🛑 Server stopped");
        }));
    }

    // ----------------- NEW: Database Test -----------------
    private static void testDatabaseConnection(HttpExchange exchange) {
        try {
            DBConnection.getInstance();
            sendResponse(exchange, 200, new MessageResponse("✅ OceanView MySQL database connected successfully!"));
        } catch (SQLException | IOException e) {
            try {
                sendResponse(exchange, 500, new MessageResponse("❌ Database connection failed: " + e.getMessage()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // ----------------- Utilities -----------------
    private static String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".json")) return "application/json";
        if (path.endsWith(".png")) return "image/png";
        return "text/plain";
    }

    private static void serveStaticFile(HttpExchange exchange, String path, String contentType) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            String response = "File Not Found: " + path;
            exchange.sendResponseHeaders(404, response.length());
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
            return;
        }

        exchange.getResponseHeaders().add("Content-Type", contentType);
        byte[] bytes = Files.readAllBytes(file.toPath());
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    public static void addCORS(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods",  "GET, POST, OPTIONS, PUT, DELETE");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "*");
    }

    private static void send405(HttpExchange exchange) throws IOException {
        addCORS(exchange);
        exchange.sendResponseHeaders(405, -1);
    }

    // ✅ FIXED: Make public static for controllers to use
    public static void sendResponse(HttpExchange exchange, int statusCode, Object data) throws IOException {
    String jsonResponse = gson.toJson(data);

    addCORS(exchange);
    exchange.getResponseHeaders().set("Content-Type", "application/json");

    exchange.sendResponseHeaders(statusCode, jsonResponse.getBytes().length);
    OutputStream os = exchange.getResponseBody();
    os.write(jsonResponse.getBytes());
    os.close();
}

    // ----------------- Response Models -----------------
    static class MessageResponse {
        public String message;
        public MessageResponse(String message) { this.message = message; }
    }

    static class ReportResponse {
        public int total_reservations;
        public double total_income;
        public ReportResponse(int total_reservations, double total_income) {
            this.total_reservations = total_reservations;
            this.total_income = total_income;
        }
    }
}
