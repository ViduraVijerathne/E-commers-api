/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dto.ProductDTO;
import dto.ServiceResponse;
import dto.ServiceResponseObject;
import dto.UserDTO;
import entity.CategoryEntity;
import entity.ProductEntity;
import entity.ShopEntity;
import exceptions.ServiceException;
import exceptions.ValidationException;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import repository.CategoryRepository;
import repository.ProductRepository;
import repository.ShopRepository;
import repository.UserRepository;

/**
 *
 * @author vidur
 */
public class ProductService implements Service {

    private UserRepository userRepository;
    private ShopRepository shopRepository;
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public ProductService() {
        userRepository = new UserRepository();
        shopRepository = new ShopRepository();
        categoryRepository = new CategoryRepository();
        productRepository = new ProductRepository();
    }

    public ServiceResponse register(ProductDTO product, UserDTO user) throws ServiceException {
        ServiceResponse response = new ServiceResponse();

        try {
            if (product.isValidate()) {
                List<ShopEntity> shop = shopRepository.getByUserId(user.getId());
                if (!shop.isEmpty()) {

                    CategoryEntity categoryEntity = new CategoryEntity();
                    try {
                        categoryEntity = categoryRepository.getById(product.getCategory().getId());
                        categoryEntity.getId();
                    } catch (ObjectNotFoundException ex) {
                        throw new ServiceException(new ServiceResponseObject(false, "invalid category ID").toString(), 400);

                    }
                    if (categoryEntity.getId() != 0) {
                        ProductEntity entity = product.toEntity();
                        entity.setCategory(categoryEntity);
                        entity.setShop(shop.get(0));
                        ProductEntity savedProduct = productRepository.Save(entity);
                        response.setData(new ServiceResponseObject(true, savedProduct.toDTO()));
                        response.setStatusCode(200);
                    } else {
                        throw new ServiceException(new ServiceResponseObject(false, "invalid category ID").toString(), 400);
                    }

                } else {
                    throw new ServiceException(new ServiceResponseObject(false, "use has no any shop").toString(), 400);
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
