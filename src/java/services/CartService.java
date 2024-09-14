/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.google.gson.Gson;
import dto.AddressBookDTO;
import dto.CartDTO;
import dto.OrderDTO;
import dto.OrderItemDTO;
import dto.OrderStatus;
import dto.PaymentObject;
import dto.ServiceResponse;
import dto.ServiceResponseObject;
import dto.StockDTO;
import dto.UserDTO;
import dto.WishlistDTO;
import entity.AddressBookEntity;
import entity.CartEntity;
import entity.OrderEntity;
import entity.OrderItemEntity;
import entity.StocksEntity;
import entity.UserEntity;
import exceptions.ServiceException;
import exceptions.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.ObjectNotFoundException;
import repository.AddressBookRepository;
import repository.CartRepository;
import repository.OrderRepository;
import repository.ProductRepository;
import repository.StockRepository;
import repository.UserRepository;
import utils.PaymentUtils;

/**
 *
 * @author vidur
 */
public class CartService implements Service {

    private StockRepository stockRepository;
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private AddressBookRepository addressBookRepository;
    private OrderRepository orderRepository;

    public CartService() {
        this.stockRepository = new StockRepository();
        this.userRepository = new UserRepository();
        this.cartRepository = new CartRepository();
        this.addressBookRepository = new AddressBookRepository();
        this.orderRepository = new OrderRepository();

    }

    public ServiceResponse addToCart(CartDTO dto, UserDTO user) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            if (dto.isValidate()) {
                try {
                    StocksEntity stocksEntity = stockRepository.get(dto.getStocksId());
                    UserEntity userEntity = userRepository.getByEmail(user.getEmail());

                    CartEntity cartEntity = new CartEntity();
                    cartEntity.setCartQty(dto.getCartQty());
                    cartEntity.setStocks(stocksEntity);
                    cartEntity.setUser(userEntity);
                    cartEntity.setStocksId(stocksEntity.getId());
                    cartEntity.setUserId(userEntity.getId());

                    CartEntity existOne = cartRepository.get(stocksEntity.getId(), userEntity.getId());
                    if (existOne == null) {
                        CartEntity savedEntity = cartRepository.save(cartEntity);
                        response.setData(new ServiceResponseObject(true, savedEntity.toDTO()));
                        response.setStatusCode(200);
                    } else {
                        existOne.setCartQty(existOne.getCartQty() + cartEntity.getCartQty());
                        CartEntity updatedEntity = cartRepository.update(existOne);
                        response.setData(new ServiceResponseObject(true, updatedEntity.toDTO()));
                        response.setStatusCode(201);
                    }

                } catch (ObjectNotFoundException ex) {
                    throw new ServiceException(new ServiceResponseObject(false, " stock not found").toString(), 400);

                } catch (NullPointerException ex) {
                    throw new ServiceException(new ServiceResponseObject(false, " stock not found").toString(), 400);

                }
            }
        } catch (ValidationException ex) {
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);

        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);

        }
        return response;
    }

    public ServiceResponse getAll(UserDTO user) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            UserEntity entity = userRepository.getByEmail(user.getEmail());
            if (entity != null) {
                List<CartEntity> entities = cartRepository.getAll(entity.getId());
                List<CartDTO> dtos = new ArrayList<>();
                for (CartEntity e : entities) {
                    dtos.add(e.toDTO());
                }
                System.out.println("=========================");
                System.out.println(dtos.size());
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

    public ServiceResponse removeFromCart(CartDTO dto, UserDTO user) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            if (dto.isValidate()) {
                try {
                    StocksEntity stocksEntity = stockRepository.get(dto.getStocksId());
                    UserEntity userEntity = userRepository.getByEmail(user.getEmail());

                    CartEntity cartEntity = new CartEntity();
                    cartEntity.setCartQty(dto.getCartQty());
                    cartEntity.setStocks(stocksEntity);
                    cartEntity.setUser(userEntity);
                    cartEntity.setStocksId(stocksEntity.getId());
                    cartEntity.setUserId(userEntity.getId());

                    CartEntity existOne = cartRepository.get(stocksEntity.getId(), userEntity.getId());
                    if (existOne == null) {
                        throw new ServiceException(new ServiceResponseObject(false, "this cart not exist in your cart").toString(), 400);

                    } else {
                        CartEntity removedEntity = cartRepository.remove(existOne);
                        response.setData(new ServiceResponseObject(true, removedEntity.toDTO()));
                        response.setStatusCode(200);
                    }
                } catch (ObjectNotFoundException ex) {
                    throw new ServiceException(new ServiceResponseObject(false, " stock  not found in your cart").toString(), 400);

                }
            }
        } catch (ValidationException ex) {
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);

        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);

        }
        return response;

    }

    synchronized private OrderEntity makeOrderObject(List<CartEntity> cart, UserDTO user) throws ServiceException {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDatetime(new Date());
        orderEntity.setRemarks("");
        orderEntity.setStatus(OrderStatus.PAYMENTPROCESSING);
        orderEntity.setOrderItems(new ArrayList<>());

        try {
            UserEntity userEntity = userRepository.getByEmail(user.getEmail());
            orderEntity.setUser(userEntity);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, "user not found").toString(), 400);

        }
        for (CartEntity cartItem : cart) {
            StocksEntity stock = stockRepository.get(cartItem.getStocksId());
            if (stock != null) {
                if (cartItem.getCartQty() > stock.getQuantity()) {
                    throw new ServiceException(new ServiceResponseObject(false, "that quantity not found in " + cartItem.getStocks().getProduct().getName()).toString(), 400);

                } else {
                    OrderItemEntity itemEntity = new OrderItemEntity();
                    itemEntity.setStocks(stock);
                    itemEntity.setQty(cartItem.getCartQty());
                    itemEntity.setStatus(OrderStatus.PAYMENTPROCESSING);
                    orderEntity.addOrderItem(itemEntity);
                }
            } else {
                throw new ServiceException("stock not found!", 400);

            }
        }
        return orderEntity;
    }

    public ServiceResponse checkOut(AddressBookDTO address, UserDTO user) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        if (address == null) {
            throw new ServiceException(new ServiceResponseObject(false, "address not found").toString(), 400);
        }
        try {
            if (address.getId() > 0) {
                try {
                    AddressBookEntity addressEntity = addressBookRepository.get(address.getId());
                    if (addressEntity != null) {
                        if (addressEntity.getUser().getId() == user.getId()) {
                            List<CartEntity> cart = cartRepository.getAll(user.getId());
                            if (cart != null) {
                                if (!cart.isEmpty()) {
                                    OrderEntity orderEntity = makeOrderObject(cart, user);
                                    orderEntity.setAddressBook(addressEntity);
                                    OrderEntity savedEntity = orderRepository.save(orderEntity);
                                    for (OrderItemEntity i : savedEntity.getOrderItems()) {
                                        int stockID = i.getStocks().getId();
                                        int qty = i.getQty();
                                        stockRepository.substractStock(stockID, qty);
                                    }
//                                    for (CartEntity c : cart) {
//                                        CartEntity removedEntity = cartRepository.remove(c);
//                                    }
                                    PaymentObject payhereObj = PaymentUtils.makePayhereObject(orderEntity);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(payhereObj);

                                    response.setData(new ServiceResponseObject(true, payhereObj));
                                    response.setStatusCode(200);
                                } else {
                                    throw new ServiceException(new ServiceResponseObject(false, "cart is empty").toString(), 400);

                                }
                            } else {
                                throw new ServiceException(new ServiceResponseObject(false, "cart not exist").toString(), 400);

                            }
                        } else {
                            throw new ServiceException(new ServiceResponseObject(false, "address is not yours").toString(), 400);

                        }
                    } else {
                        throw new ServiceException(new ServiceResponseObject(false, "address not found").toString(), 400);
                    }
                } catch (ObjectNotFoundException ex) {
                    ex.printStackTrace();
                    throw new ServiceException(new ServiceResponseObject(false, "address not found").toString(), 400);

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    throw new ServiceException(new ServiceResponseObject(false, "address not found").toString(), 400);

                }

            } else {
                throw new ValidationException("please enter address");
            }
        } catch (ValidationException ex) {
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);

        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);

        }
        return response;
    }

    synchronized public ServiceResponse checkOut(OrderDTO order) throws ServiceException {
        ServiceResponse response = new ServiceResponse();

        if (order.getAddressBook() == null || order.getAddressBook().getId() <= 0) {
            throw new ServiceException(new ServiceResponseObject(false, "address not found").toString(), 400);
        }
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new ServiceException(new ServiceResponseObject(false, "no order item not found").toString(), 400);
        }
        if (order.getOrderItems().get(0).getStock() == null || order.getOrderItems().get(0).getStock().getId() <= 0) {
            throw new ServiceException(new ServiceResponseObject(false, "no stock found").toString(), 400);
        }

        UserEntity user;
        try {
            user = userRepository.get(order.getUser().getId());
            user.getEmail();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, "no user found").toString(), 400);

        }

        StockDTO stock = order.getOrderItems().get(0).getStock();
        OrderItemDTO orderItem = order.getOrderItems().get(0);

        StocksEntity stockEntity;
        try {
            stockEntity = stockRepository.get(stock.getId());
            stock.getColor();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, "no stock found").toString(), 400);
        }

        if (stockEntity.getQuantity() < orderItem.getQty()) {
            throw new ServiceException(new ServiceResponseObject(false, "no quantity to buy this item ").toString(), 400);
        }

        AddressBookEntity address;
        try {
            address = addressBookRepository.get(order.getAddressBook().getId());
            if (address == null || address.getCity() == null) {
                throw new Exception("invalid address id");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, "invalid address id").toString(), 400);
        }

        List<OrderItemEntity> items = new ArrayList<>();
        OrderItemEntity item = new OrderItemEntity();
        
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAddressBook(address);
        orderEntity.setStatus(OrderStatus.PAYMENTPROCESSING);
        orderEntity.setDatetime(new Date());
        orderEntity.setUser(user);
        orderEntity.setOrderItems(items);
        
        item.setStatus(OrderStatus.ORDERPROCESSING);
        item.setStocks(stockEntity);
        item.setQty(orderItem.getQty());
        item.setId(0);
        orderEntity.addOrderItem(item);
        try {
            
            OrderEntity saved = orderRepository.save(orderEntity);
            stockEntity.setQuantity(stockEntity.getQuantity() - item.getQty());
            stockRepository.update(stockEntity);
            PaymentObject payhereObj = PaymentUtils.makePayhereObject(saved);
            response.setData(new ServiceResponseObject(true, payhereObj));
            response.setStatusCode(200);
        } catch (Exception ex) {
            ex.printStackTrace();

            throw new ServiceException(new ServiceResponseObject(false, "something went wrong ").toString(), 400);

        }

        return response;
    }
}
