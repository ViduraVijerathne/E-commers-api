/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author vidur
 */
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import config.MyGson;
import entity.AddressBookEntity;
import exceptions.ValidationException;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.Validators;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressBookDTO implements Serializable {

    @Expose
    private int id;

    @Expose
    private String city;

    @Expose
    private String line1;

    @Expose
    private String line2;

    @Expose
    private String postalcode;

    @Expose
    private String contact;

    @Expose
    private String name;

    @Expose
    private DistrictDTO district;
    @Expose
    private UserDTO user;

    public boolean isValidate() throws ValidationException {
        if (city == null || city.isEmpty()) {
            throw new ValidationException("City cannot be empty");
        }
        if (city.length() > 44) {
            throw new ValidationException("city name too long");

        }
        if (line1 == null || line1.isEmpty()) {
            throw new ValidationException("Address line 1 cannot be empty");
        }
        if (postalcode == null || postalcode.isEmpty()) {
            throw new ValidationException("postal code cannot be empty");
        }
        if (line2 != null && line2.length() > 44) {
            throw new ValidationException("address line2 too long");

        }
        if (line1.length() > 44) {
            throw new ValidationException("address line too long");

        }
        if (postalcode.length() < 5) {
            throw new ValidationException("postal code must be more than 5 char");
        }
        if (postalcode.length() > 10) {
            throw new ValidationException("postal code too long");

        }
        Validators.validateInt(postalcode, "postalcode");
        return true;
    }

    @Override
    public String toString() {
        Gson gson = MyGson.excludeFieldsWithoutExposeAnnotation();
        return gson.toJson(this);
    }

    public static AddressBookDTO fromRequest(HttpServletRequest req) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(req.getReader(), AddressBookDTO.class);
    }

    public AddressBookEntity toEntity() {
        AddressBookEntity entity = new AddressBookEntity();
        entity.setId(id);
        entity.setCity(city);
        entity.setLine1(line1);
        entity.setLine2(line2);
        entity.setPostalcode(postalcode);
        entity.setContact(contact);
        entity.setName(name);
        entity.setDistrict(district.toEntity());
        entity.setUser(user.toEntity());
        return entity;
    }
}
