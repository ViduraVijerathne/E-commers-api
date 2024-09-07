/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.pub;

import dto.ServiceResponse;
import exceptions.ServiceException;
import exceptions.ValidationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.ProductService;
import utils.Validators;

/**
 *
 * @author vidur
 */
@WebServlet(name = "Products", urlPatterns = {"/products"})
public class Products extends HttpServlet {

    private ProductService ProductService;

    @Override
    public void init() throws ServletException {
        ProductService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pid = req.getParameter("pid");
        String shopid = req.getParameter("shopid");
        String productName = req.getParameter("productName");
        String limit = req.getParameter("limit");
        String categoryId = req.getParameter("catId");
        String gender = req.getParameter("gender");
        String priceGreaterThan = req.getParameter("priceGreaterThan");
        String priceLowerThan = req.getParameter("priceGreaterThan");

        try {
            ServiceResponse response = ProductService.search(pid, shopid, productName, limit, categoryId, gender, priceGreaterThan, priceLowerThan);
            resp.getWriter().print(response);
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            ex.printStackTrace();
            Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
