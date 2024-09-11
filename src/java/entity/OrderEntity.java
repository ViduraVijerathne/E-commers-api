/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author vidur
 */
import dto.OrderDTO;
import dto.OrderStatus;
import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "qty", nullable = false)
    private int qty;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "addressbook_id", nullable = false)
    private AddressBookEntity addressBook;

    public OrderDTO toDTO() {
        OrderDTO dto = new OrderDTO();
        dto.setId(id);
        dto.setRemarks(remarks);
        dto.setQty(qty);
        dto.setStatus(status);
        dto.setAddressBook(addressBook.toDTO());
        return dto;
    }
}

