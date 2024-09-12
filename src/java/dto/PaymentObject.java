/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.HashMap;
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
@Setter
@Getter
public class PaymentObject implements Serializable {

    @Expose
    private boolean sandbox;
    @Expose
    private String merchant_id;
    @Expose
    private String merchantSecret;
    @Expose
    private String notify_url;
    @Expose
    private String order_id;
    @Expose
    private String items;
    @Expose
    private double amount;
    @Expose
    private String currency;
    @Expose
    private String first_name;
    @Expose
    private String last_name;
    @Expose
    private String email;
    @Expose
    private String phone;
    @Expose
    private String address;
    @Expose
    private String city;
    @Expose
    private String country;
    @Expose
    private String deliveryAddress;
    @Expose
    private String deliveryCity;
    @Expose
    private String deliveryCountry;
    @Expose
    private String custom1;
    @Expose
    private String custom2;

}
