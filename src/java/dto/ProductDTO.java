/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import config.MyGson;
import entity.ProductEntity;
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
public class ProductDTO implements Serializable {

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private Gender gender;
    @Expose
    private double price;
    @Expose
    private double shipping;
    @Expose
    private ShopDTO shop;
    @Expose
    private CategoryDTO category;

    public boolean isValidate() throws ValidationException {
        if (name == null) {
            throw new ValidationException("please enter the product name");
        }
        if (name.length() > 44 || name.length() < 2) {
            throw new ValidationException("please enter the product name length should between 2 and 45 ");
        }

        if (description == null) {
            throw new ValidationException("please enter the product description");
        }
        if (description.length() > 44 || description.length() < 2) {
            throw new ValidationException("please enter the product description length should between 2 and 45 ");
        }
        if (gender == null) {
            throw new ValidationException("please enter the product gender type ");
        }
        if (price == 0) {
            throw new ValidationException("please enter the product price can not be 0 ");
        }

        return true;
    }

    @Override
    public String toString() {
        Gson gson = MyGson.excludeFieldsWithoutExposeAnnotation();
        return gson.toJson(this);
    }

    public static ProductDTO fromRequest(HttpServletRequest req) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(req.getReader(), ProductDTO.class);
    }

    public ProductEntity toEntity() {
        ProductEntity e = new ProductEntity();
        e.setId(id);
        e.setName(name);
        e.setDescription(description);
        e.setGender(gender);
        e.setPrice(price);
        e.setShipping(shipping);
        if (shop != null) {
            e.setShop(shop.toEntity());

        }
        if (category != null) {
            e.setCategory(category.toEntity());

        }
        return e;

    }

}
