package entity;

import dto.CartDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CartEntity class for mapping the cart table.
 */
@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Id
    @Column(name = "stocks_id", nullable = false)
    private int stocksId;

    @Column(name = "cart_qty", nullable = false)
    private int cartQty;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "stocks_id", insertable = false, updatable = false)
    private StocksEntity stocks;

    public CartDTO toDTO() {
        return new CartDTO(userId, stocksId, cartQty,stocks.toDTO());
    }
}
