/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dto.Gender;
import dto.ProductDTO;
import dto.ServiceResponse;
import dto.ServiceResponseObject;
import dto.UserDTO;
import entity.CategoryEntity;
import entity.ProductEntity;
import entity.ShopEntity;
import exceptions.ServiceException;
import exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.hibernate.ObjectNotFoundException;
import repository.CategoryRepository;
import repository.ProductRepository;
import repository.ShopRepository;
import repository.UserRepository;
import utils.Validators;

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

    public ServiceResponse search(String pid, String shopid, String productName, String limit,
            String categoryId, String gender, String priceGreaterThan,
            String priceLowerThan) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        int productID = 0;
        int shopID = 0;
        int limit_ = 0;
        int catID = 0;
        Gender gen = Gender.UNISEX;
        double pgt = -1;
        double plt = -1;

        try {
            if (pid != null) {
                Validators.validateInt(pid, "product id");
                productID = Integer.parseInt(pid);
//                if (pid.equals("0")) {
//                    throw new ValidationException("productID cannot be 0");
//                }
            }
            if (shopid != null) {
                Validators.validateInt(shopid, "shop id");
                shopID = Integer.parseInt(shopid);

            }
            if (limit != null) {
                Validators.validateInt(limit, "limit");
                limit_ = Integer.parseInt(limit);

            }
            if (categoryId != null) {
                Validators.validateInt(categoryId, "category id");
                catID = Integer.parseInt(categoryId);

            }
            if (priceGreaterThan != null) {
                Validators.validateInt(priceGreaterThan, "greater than price ");
                pgt = Double.parseDouble(priceGreaterThan);

            }
            if (priceLowerThan != null) {
                Validators.validateInt(priceLowerThan, "lower than price ");
                plt = Double.parseDouble(priceLowerThan);

            }
            if (gender != null) {
                Validators.ValidateGender(gender);
                gen = Gender.valueOf(gender);
            } else {
                gen = null;
            }

            List<ProductEntity> search = productRepository.search(productID, shopID, productName, limit_, catID, gen, pgt, plt);
            List<ProductDTO> dtos = new ArrayList<>();
            for (ProductEntity e : search) {
                dtos.add(e.toDTO());
            }
            response.setData(new ServiceResponseObject(true,dtos));
            response.setStatusCode(200);
            return response;
        } catch (ValidationException ex) {
            throw new ServiceException(ex.getMessage(), 400);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex.getMessage(), 400);

        }

    }

}
