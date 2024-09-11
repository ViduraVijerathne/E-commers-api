/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.user;

import dto.AddressBookDTO;
import dto.DistrictDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vidur
 */
@WebServlet(name = "AddressBook", urlPatterns = {"/addressBook"})
public class AddressBook extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        AddressBookDTO dto  = new AddressBookDTO(
        1,
                "city",
                "line1",
                "line2",
                "postalcode",
               "contact",
                "name",
                new DistrictDTO(1,"kurunegala")
        );
    }

}
