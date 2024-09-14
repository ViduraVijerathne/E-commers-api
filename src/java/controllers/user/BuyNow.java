/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.OrderDTO;
import dto.OrderStatus;
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
import services.CartService;
import services.OrderService;
import utils.AuthUtil;

/**
 *
 * @author vidur
 */
@WebServlet(name = "BuyNow", urlPatterns = {"/auth/user/order/buynow"})
public class BuyNow extends HttpServlet {

    private OrderService orderService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        cartService = new CartService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
//        Gson gson = new Gson();
//        JsonObject obj = gson.fromJson(req.getReader(), JsonObject.class);
//        String stockID = obj.get("stockID").getAsString();
//        String qty = obj.get("qty").getAsString();
        UserDTO user = AuthUtil.getCurrentUser(req);
        OrderDTO dto = OrderDTO.fromRequest(req, OrderDTO.class);
        dto.setUser(user);
        try {
            ServiceResponse response = cartService.checkOut(dto);
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

//    confirm 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String paymentID = req.getParameter("paymentID");
        String orderID = req.getParameter("orderID");
        UserDTO user = AuthUtil.getCurrentUser(req);
        try {
            ServiceResponse response = orderService.updateOrderStatus(user, orderID, paymentID, OrderStatus.ORDERPROCESSING);
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }

    }

}
