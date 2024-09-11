/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dto.CartDTO;
import dto.ServiceResponse;
import dto.ServiceResponseObject;
import dto.UserDTO;
import dto.WishlistDTO;
import entity.CartEntity;
import entity.StocksEntity;
import entity.UserEntity;
import exceptions.ServiceException;
import exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import repository.CartRepository;
import repository.ProductRepository;
import repository.StockRepository;
import repository.UserRepository;

/**
 *
 * @author vidur
 */
public class CartService implements Service {

    private StockRepository stockRepository;
    private UserRepository userRepository;
    private CartRepository cartRepository;

    public CartService() {
        this.stockRepository = new StockRepository();
        this.userRepository = new UserRepository();
        this.cartRepository = new CartRepository();

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
                List<CartEntity> entities = cartRepository.get(entity.getId());
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

                    }else{
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
}
