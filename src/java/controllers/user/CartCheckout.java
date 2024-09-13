/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.user;

import dto.AddressBookDTO;
import dto.DTO;
import dto.ServiceResponse;
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
@WebServlet(name = "Order", urlPatterns = {"/auth/user/cart/checkout"})
public class CartCheckout extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() throws ServletException {
        cartService = new CartService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
//        AddressBookDTO addressBookDTO = AddressBookDTO.fromRequest(req);
        AddressBookDTO addressBookDTO = DTO.fromRequest(req, AddressBookDTO.class);

        try {
            ServiceResponse response = cartService.checkOut(addressBookDTO, AuthUtil.getCurrentUser(req));
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            ex.printStackTrace();
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

}
