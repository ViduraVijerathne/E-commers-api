/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import dto.DTO;
import exceptions.ValidationException;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author vidur
 * @param <D>
 */
@NoArgsConstructor
@AllArgsConstructor
public abstract class MyEntity<D extends DTO>  implements Serializable{
    private int id;
    
    public abstract D toDTO();
    public abstract int getId();
    public abstract void setId(int id);
 
}
