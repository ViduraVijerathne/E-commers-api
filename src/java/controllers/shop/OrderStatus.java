/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.shop;

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
import services.OrderService;
import utils.AuthUtil;

/**
 *
 * @author vidur
 */
@WebServlet(name = "OrderStatus", urlPatterns = {"/auth/shop/order/status"})
public class OrderStatus extends HttpServlet {

    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        this.orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String itemID = req.getParameter("itemid");
        String orderID = req.getParameter("orderid");
        String orderStatus = req.getParameter("orderStatus");
        UserDTO user = AuthUtil.getCurrentUser(req);
        try {
            ServiceResponse response = orderService.updateOrderStatus(user, orderID, itemID, orderStatus);
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }

    }

}
