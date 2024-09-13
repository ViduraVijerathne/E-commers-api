/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import dto.Size;
import dto.StockDTO;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author vidur
 */
@Entity
@Table(name = "stocks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StocksEntity extends MyEntity<StockDTO>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "color", nullable = false, length = 6)
    private String color;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false)
    private Size size;
    
    @Column(name = "qty", nullable = false)
    private int quantity;
    
    @Column(name = "color_name", nullable = false)
    private String colorName;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    // Getters and Setters
    public StockDTO toDTO() {
        StockDTO dto = new StockDTO();
        dto.setId(id);
        dto.setColor(color);
        dto.setColorName(colorName);
        dto.setQuantity(quantity);
        dto.setSize(size);
        if (product != null) {
            dto.setProduct(product.toDTO());
        }
        return dto;
    }
}
