/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dto.OrderDTO;
import dto.OrderItemDTO;
import dto.ServiceResponse;
import dto.ServiceResponseObject;
import dto.UserDTO;
import entity.OrderEntity;
import entity.OrderItemEntity;
import entity.ShopEntity;
import entity.UserEntity;
import exceptions.ServiceException;
import exceptions.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.ObjectNotFoundException;
import repository.OrderRepository;
import repository.ShopRepository;
import repository.UserRepository;

/**
 *
 * @author vidur
 */
public class OrderService implements Service {

    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private ShopRepository shopRepository;

    public OrderService() {
        this.userRepository = new UserRepository();
        this.orderRepository = new OrderRepository();
        this.shopRepository = new ShopRepository();
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

    public ServiceResponse getAllShop(UserDTO dto) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            UserEntity userEntity = userRepository.getByEmail(dto.getEmail());
            if (userEntity != null) {
                List<ShopEntity> shops = shopRepository.getByUserId(userEntity.getId());
                if (shops != null && !shops.isEmpty()) {
                    ShopEntity shopEntity = shops.get(0);

                    List<OrderItemEntity> orders = orderRepository.get(shopEntity);
                   List<OrderEntity>  responseOrder =  convertToOrder(orders);
                    List<OrderDTO> dtos = new ArrayList<>();
                    for (OrderEntity e : responseOrder) {
                        dtos.add(e.toDTO());
                    }
                    response.setData(new ServiceResponseObject(true, dtos));
                    response.setStatusCode(200);
                } else {
                    throw new ServiceException(new ServiceResponseObject(false, " shop not found").toString(), 400);

                }
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

    private List<OrderEntity> convertToOrder(List<OrderItemEntity> orderItems) {
        Map<Integer, List<OrderItemEntity>> map = new HashMap<>();
        Map<Integer, OrderEntity> ordersEntityMap = new HashMap<>();

        for (OrderItemEntity i : orderItems) {

            //getting order entity
            int orderID = i.getOrder().getId();
            List<OrderItemEntity> itemEntity = map.get(orderID);
            if (itemEntity == null) {
                itemEntity = new ArrayList<>();
                itemEntity.add(i);
            } else {
                itemEntity.add(i);
            }
            map.put(orderID, itemEntity);

            //getting orders
            ordersEntityMap.put(orderID, i.getOrder());

        }
        List<OrderEntity> orderEntity = new ArrayList<>();
        for (int orderID : ordersEntityMap.keySet()) {
            OrderEntity order = ordersEntityMap.get(orderID);
            List<OrderItemEntity> orderItemsList = map.get(orderID);
            order.setOrderItems(orderItemsList);
            orderEntity.add(order);
        }

        return orderEntity;
    }

}
