package com.oceanviewresort.service;
import com.oceanviewresort.dao.UserDAO;

import com.oceanviewresort.model.User;


public class LoginService {
    private UserDAO  userDAO;
    


    public LoginService(){
        this.userDAO=new UserDAO();
        }

    
    public boolean login(String username, String password,String role) {
        
       if(username==null||username.isEmpty()||password==null||password.isEmpty()||role==null||role.isEmpty()){
            return false;
       }


         username=username.trim();
         password=password.trim();
         role=role.trim();
        User user=userDAO.getUser(username,password,role);
        return user!=null;
       
      

    }

    }
