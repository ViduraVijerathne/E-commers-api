/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.google.gson.Gson;
import dto.CategoryDTO;
import dto.DistrictDTO;
import dto.ServiceResponse;
import dto.ServiceResponseObject;
import entity.CategoryEntity;
import entity.DistrictEntity;
import exceptions.ServiceException;
import java.util.ArrayList;
import java.util.List;
import repository.DistrictRepository;

/**
 *
 * @author vidur
 */
public class DistrictService implements Service {

    private DistrictRepository districtRepository;

    public DistrictService() {
        this.districtRepository = new DistrictRepository();
    }

    public ServiceResponse getAll() throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            List<DistrictEntity> list = districtRepository.getAll();
            List<DistrictDTO> dtos = new ArrayList<>();
            for (DistrictEntity e : list) {
                dtos.add(e.toDTO());
            }
            Gson gson = new Gson();
            String json = gson.toJson(dtos);
            response.setData(new ServiceResponseObject(true, dtos));
            response.setStatusCode(200);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, "something went wrong").toString(), 400);

        }
        return response;

    }
}
