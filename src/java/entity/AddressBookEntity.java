/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author vidur
 */
import dto.AddressBookDTO;
import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "addressbook")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressBookEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "line1", nullable = false)
    private String line1;

    @Column(name = "line2")
    private String line2;

    @Column(name = "postalcode", nullable = false)
    private String postalcode;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private DistrictEntity district;

    public AddressBookDTO toDTO() {
        AddressBookDTO dto = new AddressBookDTO();
        dto.setId(id);
        dto.setCity(city);
        dto.setLine1(line1);
        dto.setLine2(line2);
        dto.setPostalcode(postalcode);
        dto.setContact(contact);
        dto.setName(name);
        dto.setDistrict(district.toDTO());
        return dto;
    }
}
