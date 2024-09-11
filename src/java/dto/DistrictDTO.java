/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import config.MyGson;
import entity.DistrictEntity;
import exceptions.ValidationException;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DistrictDTO  implements Serializable{

    @Expose
    private int id;

    @Expose
    private String name;

    public boolean isValidate() throws ValidationException {
        if (name == null || name.isEmpty()) {
            throw new ValidationException("District name cannot be empty");
        }
        return true;
    }

    @Override
    public String toString() {
        Gson gson = MyGson.excludeFieldsWithoutExposeAnnotation();
        return gson.toJson(this);
    }

    public static DistrictDTO fromRequest(HttpServletRequest req) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(req.getReader(), DistrictDTO.class);
    }

    public DistrictEntity toEntity() {
        DistrictEntity entity = new DistrictEntity();
        entity.setId(id);
        entity.setName(name);
        return entity;
    }
}
