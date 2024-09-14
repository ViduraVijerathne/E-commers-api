/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dto.OrderDTO;
import dto.OrderItemDTO;
import dto.OrderStatus;
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
import java.util.Objects;
import org.hibernate.ObjectNotFoundException;
import repository.OrderRepository;
import repository.ShopRepository;
import repository.UserRepository;
import utils.Validators;

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
                    List<OrderEntity> responseOrder = convertToOrder(orders);
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

    public ServiceResponse updateOrderStatus(UserDTO user, String strOrderID, String strItemID, String strStatus) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            UserEntity userEntity = userRepository.getByEmail(user.getEmail());
            if (userEntity != null) {

                Validators.validateInt(strItemID, "item id");
                Validators.validateInt(strOrderID, "Order id");
                Validators.validateOrderStatus(strStatus);

                int orderID = Integer.parseInt(strOrderID);
                int itemID = Integer.parseInt(strItemID);
                OrderStatus orderStatus = OrderStatus.valueOf(strStatus);
                OrderEntity orderEntity;
                try {
                    orderEntity = orderRepository.get(orderID);
                    orderEntity.getAddressBook();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new ServiceException(new ServiceResponseObject(false, "order  not found").toString(), 400);
                }

                for (OrderItemEntity itemEntity : orderEntity.getOrderItems()) {
                    if (!Objects.equals(itemEntity.getStocks().getProduct().getShop().getUser().getId(), userEntity.getId())) {
                        throw new ServiceException(new ServiceResponseObject(false, "you have no access to this order item").toString(), 400);
                    }
                    if (itemEntity.getId() == itemID) {

                        itemEntity.setStatus(orderStatus);
                        orderRepository.update(itemEntity);

                        for (OrderItemEntity i : orderEntity.getOrderItems()) {
                            if (i.getStatus() != i.getStatus()) {
                                response.setData(new ServiceResponseObject(true, itemEntity.toDTO()));
                                response.setStatusCode(200);
                                return response;

                            }
                        }

                        orderEntity.setStatus(orderStatus);
                        orderRepository.update(orderEntity);
                        response.setData(new ServiceResponseObject(true, itemEntity.toDTO()));
                        response.setStatusCode(200);
                        return response;

                    }
                }

                throw new ServiceException(new ServiceResponseObject(false, " order Item not found").toString(), 400);

            } else {
                throw new ServiceException(new ServiceResponseObject(false, " user not found").toString(), 400);

            }
        } catch (ServiceException ex) {
            throw ex;
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, "user not found").toString(), 400);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);

        }
    }

    public ServiceResponse updateOrderStatus(UserDTO user, String strOrderID, String paymentID, OrderStatus Status) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            UserEntity userEntity = userRepository.getByEmail(user.getEmail());
            if (userEntity != null) {

                Validators.validateInt(strOrderID, "Order id");

                int orderID = Integer.parseInt(strOrderID);
                OrderEntity orderEntity;
                try {
                    orderEntity = orderRepository.get(orderID);
                    orderEntity.getAddressBook();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new ServiceException(new ServiceResponseObject(false, "order  not found").toString(), 400);
                }

                orderEntity.setStatus(Status);
                orderEntity.setRemarks(paymentID);
                orderRepository.update(orderEntity);
                response.setData(new ServiceResponseObject(true, orderEntity.toDTO()));
                response.setStatusCode(200);
                return response;

            } else {
                throw new ServiceException(new ServiceResponseObject(false, " user not found").toString(), 400);

            }
        } catch (ServiceException ex) {
            throw ex;
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, "user not found").toString(), 400);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);

        }
    }
}
