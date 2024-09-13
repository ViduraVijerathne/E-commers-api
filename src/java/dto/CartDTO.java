package dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import config.MyGson;
import entity.CartEntity;
import exceptions.ValidationException;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CartDTO class for transferring cart data.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDTO extends DTO<CartEntity> {

    @Expose
    private int userId;

    @Expose
    private int stocksId;

    @Expose
    private int cartQty;

    @Expose
    private StockDTO stock;

    @Override
    public boolean isValidate() throws ValidationException {
        if (cartQty <= 0) {
            throw new ValidationException("Cart quantity must be greater than 0.");
        }
        if (userId <= 0) {
            throw new ValidationException("User ID must be valid.");
        }
        if (stocksId <= 0) {
            throw new ValidationException("Stocks ID must be valid.");
        }
        return true;
    }

    @Override
    public String toString() {
        Gson gson = MyGson.excludeFieldsWithoutExposeAnnotation();
        return gson.toJson(this);
    }

    public static CartDTO fromRequest(HttpServletRequest req) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(req.getReader(), CartDTO.class);
    }

    @Override
    public CartEntity toEntity() {
        CartEntity entity = new CartEntity();
        entity.setUserId(userId);
        entity.setStocksId(stocksId);
        entity.setCartQty(cartQty);
        return entity;
    }

    @Override
    public int getId() {
        throw new UnsupportedOperationException("Not supported Cart id beacuse cart table id is user and stock id yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setId(int id) {
        throw new UnsupportedOperationException("Not supported Cart id beacuse cart table id is user and stock id yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }
}
