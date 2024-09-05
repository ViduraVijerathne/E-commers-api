/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

import lombok.Getter;

/**
 *
 * @author vidur
 */
@Getter
public class ServiceException extends Exception {

    private int statusCode;

    public ServiceException(String message, int status) {
        super(message);
        this.statusCode = status;
    }

}
