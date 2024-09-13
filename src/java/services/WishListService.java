/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dto.ServiceResponse;
import dto.ServiceResponseObject;
import dto.UserDTO;
import dto.WishlistDTO;
import entity.ProductEntity;
import entity.UserEntity;
import entity.WishlistEntity;
import exceptions.ServiceException;
import exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import repository.ProductRepository;
import repository.UserRepository;
import repository.WishListRepository;

/**
 *
 * @author vidur
 */
public class WishListService implements Service {

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private WishListRepository wishListRepository;

    public WishListService() {
        this.productRepository = new ProductRepository();
        this.userRepository = new UserRepository();
        this.wishListRepository = new WishListRepository();
    }

    public ServiceResponse addToWishList(WishlistDTO dto) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            if (dto.isValidate()) {
                try {
                    ProductEntity productEntity = productRepository.getByProductID(dto.getProductId());

                    UserEntity userEntity = userRepository.getByEmail(dto.getUser().getEmail());

                    WishlistEntity entity = dto.toEntity();
                    entity.setProduct(productEntity);
                    entity.setUser(userEntity);
                    entity.setProductId(productEntity.getId());
                    entity.setUserId(userEntity.getId());

                    WishlistEntity existEntity = wishListRepository.get(entity.getUserId(), entity.getProductId());
                    if (existEntity != null) {
                        response.setData(new ServiceResponseObject(true, existEntity.toDTO()));
                        response.setStatusCode(201);
                        return response;
                    }
                    WishlistEntity savedEntity = wishListRepository.save(entity);

                    response.setData(new ServiceResponseObject(true, savedEntity.toDTO()));
                    response.setStatusCode(200);

                } catch (ObjectNotFoundException ex) {
                    throw new ServiceException(new ServiceResponseObject(false, " product not found").toString(), 400);

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

    public ServiceResponse removeFromWishList(WishlistDTO dto) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            if (dto.isValidate()) {
                try {
                    ProductEntity productEntity = productRepository.getByProductID(dto.getProductId());

                    UserEntity userEntity = userRepository.getByEmail(dto.getUser().getEmail());

                    WishlistEntity entity = dto.toEntity();
                    entity.setProduct(productEntity);
                    entity.setUser(userEntity);
                    entity.setProductId(productEntity.getId());
                    entity.setUserId(userEntity.getId());

                    WishlistEntity existEntity = wishListRepository.get(entity.getUserId(), entity.getProductId());
                    if (existEntity == null) {
                        throw new ServiceException(new ServiceResponseObject(false, "this product not exist in your WishList").toString(), 400);
                    }
                    WishlistEntity savedEntity = wishListRepository.remove(existEntity);

                    response.setData(new ServiceResponseObject(true, savedEntity.toDTO()));
                    response.setStatusCode(200);

                } catch (ObjectNotFoundException ex) {
                    throw new ServiceException(new ServiceResponseObject(false, " product not found").toString(), 400);

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

    public ServiceResponse getAll(UserDTO dto) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
//            if (true) {
            try {
                UserEntity entity = userRepository.getByEmail(dto.getEmail());
                List<WishlistEntity> entities = wishListRepository.getAllByUser(entity);
                List<WishlistDTO> dtos = new ArrayList<>();
                for (WishlistEntity e : entities) {
                    dtos.add(e.toDTO());
                }
                response.setData(new ServiceResponseObject(true, dtos));
                response.setStatusCode(200);
            } catch (ObjectNotFoundException ex) {
                throw new ServiceException(new ServiceResponseObject(false, " product not found").toString(), 400);

            }
//            }
        } //        catch (ValidationException ex) {
        //            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);
        //
        //        } 
        catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);

        }
        return response;
    }

}
