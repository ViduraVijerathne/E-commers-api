/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.shop;

import dto.CategoryDTO;
import dto.DTO;
import dto.Gender;
import dto.ProductDTO;
import dto.ServiceResponse;
import dto.ShopDTO;
import exceptions.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.ProductService;
import utils.AuthUtil;
import utils.MyFileManager;

/**
 *
 * @author vidur
 */
@WebServlet(name = "ProductRegistration", urlPatterns = {"/auth/shop/product"})
public class ProductRegistration extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
//        ProductDTO product = ProductDTO.fromRequest(req);
        ProductDTO product = DTO.fromRequest(req, ProductDTO.class);
        try {
            ServiceResponse response = productService.register(product, AuthUtil.getCurrentUser(req));
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }

    }

}
