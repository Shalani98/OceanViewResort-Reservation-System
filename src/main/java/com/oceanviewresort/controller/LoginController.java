package com.oceanviewresort.controller;
import com.oceanviewresort.service.LoginService;
import com.oceanviewresort.model.User;
import com.oceanviewresort.server.HttpServerMain;
import com.oceanviewresort.service.LoginService;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;



public class LoginController {

private LoginService loginService;
 private final Gson gson = new Gson();

public  LoginController(){
loginService=new LoginService();
}


public void handleLogin(HttpExchange exchange)throws IOException{
    InputStreamReader reader=new InputStreamReader(exchange.getRequestBody());
    User request=gson.fromJson(reader,User.class);


    System.out.println("USERNAME: [" + request.getUsername() + "]");
    System.out.println("PASSWORD: [" + request.getPassword() + "]");
    System.out.println("ROLE: [" + request.getRole() + "]");
    boolean isValid = loginService.login(request.getUsername(), request.getPassword(), request.getRole());

    if (isValid) {
        HttpServerMain.sendResponse(exchange, 200, "Login successful");
    } else {
        HttpServerMain.sendResponse(exchange, 401, "Invalid credentials");
    }

}



}