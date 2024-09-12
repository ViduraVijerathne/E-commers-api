package dto;

import com.google.gson.annotations.Expose;
import entity.OrderEntity;
import entity.OrderItemEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemDTO implements Serializable {

    @Expose
    private int id;
    @Expose
    private int qty;
    @Expose
    private int orderId;
    @Expose
    private StockDTO stock;
    @Expose
    private OrderStatus status; // Create an Enum for status

    public OrderItemEntity toEntity() {
        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        return new OrderItemEntity(id, qty, order, stock.toEntity(),status);
    }

    public OrderItemEntity toEntity(OrderEntity entity) {
       
        return new OrderItemEntity(id, qty, entity, stock.toEntity(),status);
    }
}
