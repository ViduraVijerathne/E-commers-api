/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author vidur
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopDTO implements Serializable {

    private int id;
    private String shopName;
    private String shopAddress;
    private String city;
    private String district;
    private String contact;
    private UserDTO user;
}
