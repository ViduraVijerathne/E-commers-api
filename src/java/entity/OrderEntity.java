package entity;

import dto.OrderDTO;
import dto.OrderStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "remarks", length = 45)
    private String remarks;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // Create an Enum for status

    @Column(name = "datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @ManyToOne
    @JoinColumn(name = "addressbook_id", nullable = false)
    private AddressBookEntity addressBook;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems;
    
    
    public OrderDTO toDTO() {
        OrderDTO dto = new OrderDTO();
        dto.setId(this.id);
        dto.setRemarks(this.remarks);
        dto.setStatus(this.status);
        dto.setDatetime(this.datetime);
        dto.setAddressBook(this.addressBook.toDTO());
        dto.setUser(this.user.toDTO());
        dto.setOrderItems(this.orderItems.stream().map(OrderItemEntity::toDTO).collect(Collectors.toList()));
        return dto;
    }
    
     // Add method to set order to each OrderItem
    public void addOrderItem(OrderItemEntity orderItem) {
        orderItem.setOrder(this);  // Set this order to orderItem
        this.orderItems.add(orderItem);
    }
}
