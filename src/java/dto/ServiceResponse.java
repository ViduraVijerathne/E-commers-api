/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author vidur
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ServiceResponse {
    private int statusCode;
    private ServiceResponseObject data;

    @Override
    public String toString() {
        return data.toString();
    }
    
    
    
}
