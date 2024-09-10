/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.pub;

import dto.ServiceResponse;
import exceptions.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.StockService;
import utils.Validators;

/**
 *
 * @author vidur
 */
@WebServlet(name = "Stocks", urlPatterns = {"/product/stocks"})
public class Stocks extends HttpServlet {

    private StockService stockService;

    @Override
    public void init() throws ServletException {
        stockService = new StockService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pid = req.getParameter("pid");
        try {
            ServiceResponse response = stockService.getProductStocks(pid);
            resp.getWriter().print(response);
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            ex.printStackTrace();
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
