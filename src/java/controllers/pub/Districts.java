/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.pub;

import dto.DTO;
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
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.DistrictService;

/**
 *
 * @author vidur
 */
@WebServlet(name = "Districts", urlPatterns = {"/district"})
public class Districts extends HttpServlet {

    private DistrictService districtService;

    @Override
    public void init() throws ServletException {
        districtService = new DistrictService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            ServiceResponse response = districtService.getAll();
//            List<CategoryDTO> dtos  = new Gson().fromJson(response.getData(), classOfT);
            resp.getWriter().print(response);
            resp.setStatus(response.getStatusCode());
        } catch (ServiceException ex) {
            resp.setStatus(ex.getStatusCode());
            resp.getWriter().write(ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        DistrictDTO dto = DTO.fromRequest(req, DistrictDTO.class);
//        Session session = config.HibernateUtil.getSessionFactory().openSession();
//        Transaction t = session.getTransaction();
//        t.begin();
//        session.save(dto.toEntity());
//        t.commit();
//        resp.getWriter().write(dto.toString());
    }
    
    

}
