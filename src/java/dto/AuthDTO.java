/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import com.google.gson.Gson;
import config.MyGson;
import exceptions.ValidationException;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
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
public class AuthDTO implements Serializable {

    private int id;
    private String email;
    private String password;
    private String vc;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public boolean isValidate() throws ValidationException {

        Validators.validateEmail(email);
        Validators.validatePassword(password);

        return true;
    }
    
    public static AuthDTO fromRequest(HttpServletRequest req) throws IOException{
        Gson gson = new Gson();
        return gson.fromJson(req.getReader(), AuthDTO.class);
    }

}
