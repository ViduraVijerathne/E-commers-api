/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author vidur
 */
public class MyGson {
    
    public static Gson excludeFieldsWithoutExposeAnnotation(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }
}
