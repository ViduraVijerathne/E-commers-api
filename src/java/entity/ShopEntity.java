/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import com.google.gson.Gson;
import config.MyGson;
import dto.ShopDTO;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author vidur
 */
@Entity
@Table(name = "shop")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "shop_address", nullable = false)
    private String shopAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "contact", nullable = false, length = 10)
    private String contact;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    
    // Getters and Setters

    @Override
    public String toString() {
        Gson gson = MyGson.excludeFieldsWithoutExposeAnnotation();
        return gson.toJson(this);
    }
    
    public ShopDTO toDTO(){
        ShopDTO dto = new ShopDTO();
        dto.setId(id);
        dto.setShopName(shopName);
        dto.setShopAddress(shopAddress);
        dto.setCity(city);
        dto.setDistrict(district);
        dto.setContact(contact);
        dto.setUser(user.toDTO());
        return dto;
    }
}

