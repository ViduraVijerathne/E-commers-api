/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filters;

import dto.ServiceResponseObject;
import dto.UserDTO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.AuthUtil;

/**
 *
 * @author vidur
 */
//auth
public class UserAuthCheck implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (AuthUtil.isVerifiedAuthenticatedUser(httpServletRequest)) {
            chain.doFilter(request, response);

        } else {
            httpServletResponse.setContentType("application/json");
            ServiceResponseObject obj = new ServiceResponseObject(false, "no Access");
            httpServletResponse.getWriter().write(obj.toString());
            httpServletResponse.setStatus(401);
        }

//        UserDTO userDTO = (UserDTO) httpServletRequest.getSession().getAttribute("user");
//        httpServletRequest.getSession().removeAttribute("user");
//        if (httpServletRequest.getSession().getAttribute("user") != null) {
//            httpServletResponse.getWriter().write("filter is working");
//            chain.doFilter(request, response);
//        } else {
//            httpServletResponse.sendRedirect("sign-in.html");
//        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("User AuthCheck Filter Init");
    }

    @Override
    public void destroy() {
        System.out.println("User AuthCheck Filter Destroy");
    }

}
