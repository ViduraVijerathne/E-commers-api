/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.shop;

import dto.Gender;
import dto.ProductDTO;
import dto.ServiceResponse;
import dto.Size;
import dto.StockDTO;
import exceptions.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.ProductService;
import services.StockService;
import utils.AuthUtil;

/**
 *
 * @author vidur
 */
@WebServlet(name = "ProductStock", urlPatterns = {"/auth/shop/product/stock"})
public class ProductStock extends HttpServlet {
    
    StockService stockService;
    @Override
    public void init() throws ServletException {
        stockService = new StockService();
    }
    
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        StockDTO stock = StockDTO.fromRequest(req);
       try{
            ServiceResponse response = stockService.AddStock(stock);
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

}
