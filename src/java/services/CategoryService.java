/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.google.gson.Gson;
import dto.CategoryDTO;
import dto.ServiceResponse;
import dto.ServiceResponseObject;
import entity.CategoryEntity;
import exceptions.ServiceException;
import java.util.ArrayList;
import java.util.List;
import repository.CategoryRepository;

/**
 *
 * @author vidur
 */
public class CategoryService implements Service {

    CategoryRepository repository;

    public CategoryService() {
        repository = new CategoryRepository();
    }

    public ServiceResponse getAll() throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            List<CategoryEntity> list = repository.getAll();
            List<CategoryDTO> dtos = new ArrayList<>();
            for(CategoryEntity e : list){
                dtos.add(e.toDTO());
            }
            Gson gson = new Gson();
            String json  = gson.toJson(dtos);
            response.setData(new ServiceResponseObject(true,dtos));
            response.setStatusCode(200);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, "something went wrong").toString(), 400);

        }
        return response;
    }

}
