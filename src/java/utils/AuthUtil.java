/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import dto.UserDTO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vidur
 */
public class AuthUtil {

    public static boolean isVerifiedAuthenticatedUser(HttpServletRequest httpServletRequest) {
        UserDTO userDTO = (UserDTO) httpServletRequest.getSession().getAttribute("user");
        if (userDTO == null) {
            return false;
        } else {
            return userDTO.isVerified();
        }

    }

    public static UserDTO getCurrentUser(HttpServletRequest httpServletRequest) {
        UserDTO userDTO = (UserDTO) httpServletRequest.getSession().getAttribute("user");
        return userDTO;
    }

}
