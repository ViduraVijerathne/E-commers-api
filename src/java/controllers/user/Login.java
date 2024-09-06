/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.user;

import dto.AuthDTO;
import dto.ServiceResponse;
import exceptions.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.UserServices;

/**
 *
 * @author vidur
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    UserServices userServices;

    @Override
    public void init() throws ServletException {
        userServices = new UserServices();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        AuthDTO auth = AuthDTO.fromRequest(req);
        try {
            HttpSession httpSession = req.getSession();
            ServiceResponse response = userServices.login(auth, httpSession);

            resp.getWriter().print(response.getData().toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("maru");
    }
    
    

}
