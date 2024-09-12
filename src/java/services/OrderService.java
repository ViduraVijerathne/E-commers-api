/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dto.OrderDTO;
import dto.ServiceResponse;
import dto.ServiceResponseObject;
import dto.UserDTO;
import entity.OrderEntity;
import entity.UserEntity;
import exceptions.ServiceException;
import exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import repository.OrderRepository;
import repository.UserRepository;

/**
 *
 * @author vidur
 */
public class OrderService implements Service {

    private UserRepository userRepository;
    private OrderRepository orderRepository;

    public OrderService() {
        this.userRepository = new UserRepository();
        this.orderRepository = new OrderRepository();
    }

    public ServiceResponse getAll(UserDTO dto) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            UserEntity entity = userRepository.getByEmail(dto.getEmail());
            if (entity != null) {
                List<OrderEntity> orders = orderRepository.get(entity);
                List<OrderDTO> dtos = new ArrayList<>();
                for (OrderEntity e : orders) {
                    dtos.add(e.toDTO());
                }
                response.setData(new ServiceResponseObject(true, dtos));
                response.setStatusCode(200);
            } else {
                throw new ServiceException(new ServiceResponseObject(false, " user not found").toString(), 400);

            }
        } catch (ServiceException ex) {
            throw ex;
        } catch (ObjectNotFoundException ex) {
            throw new ServiceException(new ServiceResponseObject(false, " user not found").toString(), 400);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);

        }
        return response;
    }

}
