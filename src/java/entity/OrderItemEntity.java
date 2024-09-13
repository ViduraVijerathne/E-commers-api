package entity;

import dto.OrderItemDTO;
import dto.OrderStatus;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemEntity extends MyEntity<OrderItemDTO>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "qty", nullable = false)
    private int qty;

    @ManyToOne
    @JoinColumn(name = "orders_id", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "stocks_id", nullable = false)
    private StocksEntity stocks;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // Create an Enum for status

    @Override
     public OrderItemDTO toDTO() {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(this.id);
        dto.setQty(this.qty);
        dto.setOrderId(this.order.getId());
        dto.setStock(this.stocks.toDTO());
        dto.setStatus(status);
        return dto;
    }
}
