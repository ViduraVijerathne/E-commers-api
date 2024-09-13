/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import com.google.gson.annotations.Expose;
import entity.CartEntity;
import entity.CategoryEntity;
import exceptions.ValidationException;
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
public class CategoryDTO extends DTO<CategoryEntity>{

    @Expose
    private int id;
    @Expose
    private String name;
    @Override
    public CategoryEntity toEntity(){
        return new CategoryEntity(id,name);
    }

    @Override
    public boolean isValidate() throws ValidationException {
        if(name.length() > 45){
            throw new ValidationException("category name maximum limit 45");
        }
        return true;
    }
    
}
