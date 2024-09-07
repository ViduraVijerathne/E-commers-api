/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.pub;

import com.google.gson.Gson;
import dto.CategoryDTO;
import dto.ServiceResponse;
import exceptions.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.CategoryService;
import utils.AuthUtil;

/**
 *
 * @author vidur
 */
@WebServlet(name = "Categories", urlPatterns = {"/category"})
public class Categories extends HttpServlet {
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
    categoryService = new CategoryService();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            ServiceResponse response = categoryService.getAll();
//            List<CategoryDTO> dtos  = new Gson().fromJson(response.getData(), classOfT);
            resp.getWriter().print(response);
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

}
