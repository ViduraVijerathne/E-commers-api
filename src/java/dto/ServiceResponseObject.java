/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import config.MyGson;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author vidur
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceResponseObject implements Serializable {

    @Expose
    private boolean sucsess;
    @Expose
    private Object data;

    @Override
    public String toString() {
        Gson gson = MyGson.excludeFieldsWithoutExposeAnnotation();
        return gson.toJson(this);
    }

}
