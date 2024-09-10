/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.user;

import dto.ProductDTO;
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
import services.WishListService;
import utils.AuthUtil;

/**
 *
 * @author vidur
 */
@WebServlet(name = "WishList", urlPatterns = {"/auth/user/wishlist"})
public class WishList extends HttpServlet {

    private WishListService wishListService;

    @Override
    public void init() throws ServletException {
        wishListService = new WishListService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        UserDTO currentUser = AuthUtil.getCurrentUser(req);
        WishlistDTO whishList = WishlistDTO.fromRequest(req);
        whishList.setUser(currentUser);

        try {
            ServiceResponse response = wishListService.addToWishList(whishList);
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            ex.printStackTrace();
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

    //remove from wishlist
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        UserDTO currentUser = AuthUtil.getCurrentUser(req);
        WishlistDTO whishList = WishlistDTO.fromRequest(req);
        whishList.setUser(currentUser);

        try {
            ServiceResponse response = wishListService.removeFromWishList(whishList);
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
            ServiceResponse response = wishListService.getAll(currentUser);
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            ex.printStackTrace();
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }
    
    

}
