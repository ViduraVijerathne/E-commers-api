/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.user;

import dto.ServiceResponse;
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
@WebServlet(name = "Orders", urlPatterns = {"/auth/user/order"})
public class Orders extends HttpServlet {

    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        this.orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            ServiceResponse response = orderService.getAll(AuthUtil.getCurrentUser(req));
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            ex.printStackTrace();
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

}
