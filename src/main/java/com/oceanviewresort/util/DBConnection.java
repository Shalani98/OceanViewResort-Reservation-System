package com.oceanviewresort.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // XAMPP MySQL - localhost:3306, root/no password (default)
// In DBConnection.java - CHANGE THIS:
private static final String URL = "jdbc:mysql://localhost:3306/oceanview?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
private static final String USERNAME = "root";  
private static final String PASSWORD = "";  // XAMPP default


    private static Connection connection;

    private DBConnection() {}

    public static Connection getInstance() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // MySQL JDBC Driver (auto-loaded in modern versions, but explicit for safety)
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("🟢 MySQL Driver loaded successfully!");

                // Connect to XAMPP MySQL
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✅ Connected to OceanViewResort MySQL database!");
                System.out.println("🌊 XAMPP MySQL ready at localhost:3306");

            } catch (ClassNotFoundException e) {
                System.err.println("❌ MySQL JDBC Driver NOT FOUND!");
                System.err.println("📥 Download: mysql-connector-j-8.0.33.jar");
                System.err.println("📂 Add to your project lib/ folder");
                e.printStackTrace();
                throw new SQLException("MySQL Driver missing", e);
            } catch (SQLException e) {
                System.err.println("❌ XAMPP MySQL Connection FAILED!");
                System.err.println("🔍 Check: XAMPP MySQL running? Database exists?");
                System.err.println("💡 URL: " + URL);
                e.printStackTrace();
                throw e;
            }
        }
        return connection;
    }
}