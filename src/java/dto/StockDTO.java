/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import config.MyGson;
import entity.StocksEntity;
import exceptions.ValidationException;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.Validators;

/**
 *
 * @author vidur
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockDTO extends DTO<StocksEntity> {

    @Expose
    private int id;
    @Expose
    private String color;
    @Expose
    private Size size;
    @Expose
    private int quantity;
    @Expose
    private String colorName;
    @Expose
    private ProductDTO product;

    @Override
    public boolean isValidate() throws ValidationException {
        if (color == null) {
            throw new ValidationException("please enter color ");

        }
        if (!Validators.validateColor(color)) {
            throw new ValidationException("color is not compatible hexa decimal ");
        }
        if (size == null) {
            throw new ValidationException("please enter Size ");

        }
        if (quantity <= 0) {
            throw new ValidationException("please enter quentity ");
        }
        if (colorName == null) {
            throw new ValidationException("please enter colorName ");

        }
        if (colorName.length() <= 2 || colorName.length() > 44) {
            throw new ValidationException("color name length should be 2 and 45  ");
        }
        if (product == null) {
            throw new ValidationException("product cannot be null");

        }

        return true;
    }

    @Override
    public String toString() {
        Gson gson = MyGson.excludeFieldsWithoutExposeAnnotation();
        return gson.toJson(this);
    }

//    public static StockDTO fromRequest(HttpServletRequest req) throws IOException {
//        Gson gson = new Gson();
//        return gson.fromJson(req.getReader(), StockDTO.class);
//    }

    @Override
    public StocksEntity toEntity() {
        StocksEntity entity = new StocksEntity();
        entity.setId(id);
        entity.setColor(color);
        entity.setColorName(colorName);
        if (product != null) {
            entity.setProduct(product.toEntity());

        }
        entity.setQuantity(quantity);
        entity.setSize(size);
        return entity;
    }

}
