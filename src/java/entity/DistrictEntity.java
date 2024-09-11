/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author vidur
 */
import dto.DistrictDTO;
import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "district")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DistrictEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    public DistrictDTO toDTO() {
        DistrictDTO dto = new DistrictDTO();
        dto.setId(id);
        dto.setName(name);
        return dto;
    }
}

