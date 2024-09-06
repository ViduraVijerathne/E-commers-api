/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.shop;

import dto.ServiceResponse;
import dto.ShopDTO;
import dto.UserDTO;
import exceptions.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.ShopService;
import utils.AuthUtil;

/**
 *
 * @author vidur
 */
@WebServlet(name = "ShopRegister", urlPatterns = {"/auth/shop/register"})
public class ShopRegister extends HttpServlet {

    private ShopService shopService;

    @Override
    public void init() throws ServletException {
        shopService = new ShopService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        ShopDTO shop = ShopDTO.fromRequest(req);
        shop.setUser(AuthUtil.getCurrentUser(req));
        try {
            ServiceResponse response = shopService.register(shop);
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        UserDTO user =  AuthUtil.getCurrentUser(req);
        try{
            ServiceResponse response = shopService.getByUser(user);
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

}
