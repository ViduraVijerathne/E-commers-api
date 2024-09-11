/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.user;

import dto.AddressBookDTO;
import dto.DistrictDTO;
import dto.ServiceResponse;
import exceptions.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AddressBookService;
import utils.AuthUtil;

/**
 *
 * @author vidur
 */
@WebServlet(name = "AddressBook", urlPatterns = {"/auth/user/addressbook"})
public class AddressBook extends HttpServlet {
    private AddressBookService addressBookService;

    @Override
    public void init() throws ServletException {
        addressBookService = new AddressBookService();
    }
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        AddressBookDTO dto = AddressBookDTO.fromRequest(req);
        dto.setUser(AuthUtil.getCurrentUser(req));
        try {
            ServiceResponse response = addressBookService.add(dto);
            resp.getWriter().print(response.toString());
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            ex.printStackTrace();
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

}
