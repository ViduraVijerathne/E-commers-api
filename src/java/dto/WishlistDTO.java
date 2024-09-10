/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import config.MyGson;
import entity.WishlistEntity;
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
public class WishlistDTO  implements Serializable {

    @Expose
    private int userId;
    
    @Expose
    private int productId;
    
    @Expose
    private UserDTO user;
    
    @Expose
    private ProductDTO product;

    public boolean isValidate() throws ValidationException {
        if (user  == null) {
            throw new ValidationException("no user");
        }
        if (productId <= 0) {
            throw new ValidationException("Invalid product ID.");
        }
        return true;
    }

    @Override
    public String toString() {
        Gson gson = MyGson.excludeFieldsWithoutExposeAnnotation();
        return gson.toJson(this);
    }

    public static WishlistDTO fromRequest(HttpServletRequest req) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(req.getReader(), WishlistDTO.class);
    }

    public WishlistEntity toEntity() {
        WishlistEntity entity = new WishlistEntity();
        entity.setUserId(userId);
        entity.setProductId(productId);
        if (user != null) {
            entity.setUser(user.toEntity());
        }
        if (product != null) {
            entity.setProduct(product.toEntity());
        }
        return entity;
    }
}
