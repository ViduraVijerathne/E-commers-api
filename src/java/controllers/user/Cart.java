/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.user;

import dto.CartDTO;
import dto.ServiceResponse;
import dto.UserDTO;
import dto.WishlistDTO;
import exceptions.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.CartService;
import utils.AuthUtil;

/**
 *
 * @author vidur
 */
@WebServlet(name = "Cart", urlPatterns = {"/auth/user/cart"})
public class Cart extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() throws ServletException {
        cartService = new CartService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        UserDTO currentUser = AuthUtil.getCurrentUser(req);
        CartDTO cart = CartDTO.fromRequest(req);
        cart.setUserId(currentUser.getId());

        try {
            ServiceResponse response = cartService.addToCart(cart, currentUser);
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            ex.printStackTrace();
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        UserDTO currentUser = AuthUtil.getCurrentUser(req);

        try {
            System.out.println("=============CONTROLLER START============================");
            ServiceResponse response = cartService.getAll(currentUser);
            System.out.println(response.toString());
            System.out.println("=============CONTROLLER END============================");

            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            ex.printStackTrace();
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        UserDTO currentUser = AuthUtil.getCurrentUser(req);
        CartDTO cart = CartDTO.fromRequest(req);
        cart.setUserId(currentUser.getId());

        try {
            ServiceResponse response = cartService.removeFromCart(cart, currentUser);
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            ex.printStackTrace();
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

}
