package com.oceanviewresort.util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL="jdbc:mysql://localhost:8080/oceanview";
    private static final String USER="";
    private static final String PASSWORD="";

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
