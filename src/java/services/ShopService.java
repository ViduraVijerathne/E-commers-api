/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dto.ServiceResponse;
import dto.ServiceResponseObject;
import dto.ShopDTO;
import dto.UserDTO;
import entity.ShopEntity;
import exceptions.ServiceException;
import exceptions.ValidationException;
import java.util.List;
import repository.ShopRepository;

/**
 *
 * @author vidur
 */
public class ShopService implements Service {

    ShopRepository shopRepository;

    public ShopService() {
        this.shopRepository = new ShopRepository();
    }

    public ServiceResponse register(ShopDTO shopDTO) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            if (shopDTO.isValidate()) {

                List<ShopEntity> shops = shopRepository.getByUserId(shopDTO.getUser().getId());

                if (shops.isEmpty()) {
                    ShopEntity entity = shopRepository.save(shopDTO);

                    response.setData(new ServiceResponseObject(true, entity));
                    response.setStatusCode(200);
                } else {
                    throw new ServiceException(new ServiceResponseObject(false, "in current situation only  one user can register one shop").toString(), 400);

                }

                response.setData(new ServiceResponseObject(true, shopDTO));
                response.setStatusCode(200);
            }
        } catch (ValidationException ex) {
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);
        }

        return response;
    }

    public ServiceResponse getByUser(UserDTO dto) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            List<ShopEntity> shops = shopRepository.getByUserId(dto.getId());
            if(shops.isEmpty()){
                response.setStatusCode(204);
                response.setData(new ServiceResponseObject(true,"no shop found"));
                return response;
            }else{
                response.setStatusCode(200);
                response.setData(new ServiceResponseObject(true,shops.get(0)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return response;
    }

}
