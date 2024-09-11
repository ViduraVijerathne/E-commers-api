/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author vidur
 */
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import entity.OrderEntity;
import exceptions.ValidationException;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO implements Serializable {

    @Expose
    private int id;

    @Expose
    private String remarks;

    @Expose
    private int qty;

    @Expose
    private OrderStatus status; 
    @Expose
    private AddressBookDTO addressBook;

    public boolean isValidate() throws ValidationException {
        if (qty <= 0) {
            throw new ValidationException("Quantity must be greater than zero");
        }
        return true;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static OrderDTO fromRequest(HttpServletRequest req) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(req.getReader(), OrderDTO.class);
    }

    public OrderEntity toEntity() {
        OrderEntity entity = new OrderEntity();
        entity.setId(id);
        entity.setRemarks(remarks);
        entity.setQty(qty);
        entity.setStatus(status);
        entity.setAddressBook(addressBook.toEntity());
        return entity;
    }
}

