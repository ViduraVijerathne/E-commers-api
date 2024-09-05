/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.Gson;
import config.MyGson;
import dto.ServiceResponse;
import dto.UserDTO;
import exceptions.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.UserServices;

/**
 *
 * @author vidur
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {
    UserServices userServices;

    @Override
    public void init() throws ServletException {
        userServices = new UserServices();
    }
    
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Gson gson = MyGson.excludeFieldsWithoutExposeAnnotation();
        UserDTO userDTO = gson.fromJson(req.getReader(),UserDTO.class);
        
        
        try{
            ServiceResponse response = userServices.register(userDTO);
            resp.getWriter().print(response.getData().toString());
            resp.setStatus(response.getStatusCode());
        }catch(ServiceException ex){
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
//        resp.getWriter().write(userDTO.toString());
    }

}
