package dto;

import com.google.gson.annotations.Expose;
import entity.OrderEntity;
import entity.OrderItemEntity;
import entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO implements Serializable {

    @Expose
    private int id;
    @Expose
    private String remarks;
    @Expose
    private OrderStatus status;  // Store as String for simplicity
    @Expose
    private Date datetime;
    @Expose
    private AddressBookDTO addressBook;
    @Expose
    private UserDTO user;
    @Expose
    private List<OrderItemDTO> orderItems;

    public OrderEntity toEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(this.id);
        orderEntity.setRemarks(this.remarks);
        orderEntity.setStatus(this.status);
        orderEntity.setDatetime(this.datetime);
        orderEntity.setAddressBook(this.addressBook.toEntity()); // Convert using given AddressBookEntity
        orderEntity.setUser(user.toEntity()); // Convert using given UserEntity
        List<OrderItemEntity> items = new ArrayList<>();
        for( OrderItemDTO i : orderItems){
            items.add(i.toEntity());
        }
        orderEntity.setOrderItems(items);
        return orderEntity;
    }
}
