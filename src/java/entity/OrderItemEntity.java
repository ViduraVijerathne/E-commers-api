package entity;

import dto.OrderItemDTO;
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
public class OrderItemEntity implements Serializable{
    
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
    
    
     public OrderItemDTO toDTO() {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(this.id);
        dto.setQty(this.qty);
        dto.setOrderId(this.order.getId());
        dto.setStock(this.stocks.toDTO());
        return dto;
    }
}
