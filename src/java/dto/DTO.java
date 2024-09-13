/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import com.google.gson.Gson;
import config.MyGson;
import entity.MyEntity;
import exceptions.ValidationException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author vidur
 * @param <E>
 */
public abstract class DTO<E extends MyEntity> {

    public abstract boolean isValidate() throws ValidationException;

    public abstract E toEntity();

    public abstract int getId();

    public abstract void setId(int id);

    @Override
    public String toString() {
        Gson gson = MyGson.excludeFieldsWithoutExposeAnnotation();
        return gson.toJson(this);
    }

    public static <T extends DTO<?>> T fromRequest(HttpServletRequest req, Class<T> dtoClass) throws IOException {
        Gson gson = MyGson.excludeFieldsWithoutExposeAnnotation();
        return gson.fromJson(req.getReader(), dtoClass);
    }

}
