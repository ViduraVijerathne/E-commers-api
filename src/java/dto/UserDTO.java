/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import config.MyGson;
import entity.UserEntity;
import exceptions.ValidationException;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.Validators;

/**
 *
 * @author vidur
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO implements Serializable {

    @Expose
    private int id;

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private String email;

    @Expose(deserialize = true, serialize = false)
    private String password;

    @Expose
    private String vc;

    @Expose
    private boolean isVerified;

    @Override
    public String toString() {
        Gson gson = MyGson.excludeFieldsWithoutExposeAnnotation();
        return gson.toJson(this);
    }

    public boolean isValidate() throws ValidationException {
        Validators.validateFirstName(firstName);
        Validators.validateLastName(lastName);
        Validators.validateEmail(email);
        Validators.validatePassword(password);

        return true;
    }
    
    
    public  UserEntity toEntity(){
        UserEntity entity = new UserEntity();
        entity.setId(id);
        entity.setEmail(email);
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setPassword(password);
        entity.setVc(vc);
        entity.setVerified(isVerified);
        return entity;
    }

}
