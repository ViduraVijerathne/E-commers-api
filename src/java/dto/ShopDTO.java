/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import config.MyGson;
import entity.ShopEntity;
import exceptions.ValidationException;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
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
public class ShopDTO extends DTO<ShopEntity> {

    @Expose
    private int id;

    @Expose
    private String shopName;

    @Expose
    private String shopAddress;

    @Expose
    private String city;

    @Expose
    private String district;

    @Expose
    private String contact;
    @Expose
    private UserDTO user;

    @Override
    public boolean isValidate() throws ValidationException {
        if (shopName == null || shopName.trim().isEmpty()) {
            throw new ValidationException("Shop name is required.");
        }

        if (shopAddress == null || shopAddress.trim().isEmpty()) {
            throw new ValidationException("Shop address is required.");
        }

        if (district == null || district.trim().isEmpty()) {
            throw new ValidationException("District is required.");
        }

        if (contact == null || contact.trim().isEmpty()) {
            throw new ValidationException("Contact is required.");
        }

        if (!contact.matches("\\d{10}")) {
            throw new ValidationException("Contact must be a valid 10-digit number.");
        }

        if (user == null) {
            throw new ValidationException("User is required.");
        }
        if (shopName.length() > 44) {
            throw new ValidationException("shop name length too long. only 45 characters ");
        }
        if (shopAddress.length() > 44) {
            throw new ValidationException("shop address length too long. only 45 characters ");
        }
        if (city.length() > 44) {
            throw new ValidationException(" city length too long. only 45 characters ");
        }
        if (district.length() > 44) {
            throw new ValidationException(" district length too long. only 45 characters ");
        }
        return true;
    }

    @Override
    public String toString() {
        Gson gson = MyGson.excludeFieldsWithoutExposeAnnotation();
        return gson.toJson(this);
    }

  

    @Override
    public ShopEntity toEntity() {
        ShopEntity entity = new ShopEntity();
        entity.setId(id);
        entity.setShopName(shopName);
        entity.setShopAddress(shopAddress);
        entity.setCity(city);
        entity.setDistrict(district);
        entity.setContact(contact);
        if (user != null) {
            entity.setUser(user.toEntity());

        }
        return entity;
    }

}
