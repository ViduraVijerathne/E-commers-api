/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dto.ProductDTO;
import dto.ServiceResponse;
import dto.ServiceResponseObject;
import dto.StockDTO;
import entity.ProductEntity;
import entity.StocksEntity;
import exceptions.ServiceException;
import exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import repository.ProductRepository;
import repository.StockRepository;
import utils.Validators;

/**
 *
 * @author vidur
 */
public class StockService implements Service {

    ProductRepository productRepository;
    StockRepository stockRepository;

    public StockService() {
        stockRepository = new StockRepository();
        productRepository = new ProductRepository();
    }

    public ServiceResponse AddStock(StockDTO stock) throws ServiceException {
        ServiceResponse response = new ServiceResponse();
        try {
            if (stock.isValidate()) {
                try {
                    ProductEntity productEntity = productRepository.getByProductID(stock.getProduct().getId());
                    productEntity.getId();
                    StocksEntity entity = stock.toEntity();
                    entity.setProduct(productEntity);
                    StocksEntity savedEntity = stockRepository.save(entity);
                    response.setData(new ServiceResponseObject(true, savedEntity.toDTO()));
                    response.setStatusCode(200);
                } catch (ObjectNotFoundException ex) {
                    throw new ServiceException(new ServiceResponseObject(false, "invalid product ID").toString(), 400);

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

    public ServiceResponse getProductStocks(String pid) throws ServiceException {
        ServiceResponse response = new ServiceResponse();

        try {
            if (Validators.validateInt(pid, "not valid product id")) {
                int productID = Integer.parseInt(pid);
                List<StocksEntity> stockEntities = stockRepository.getByProductID(productID);
                List<StockDTO> dtos = new ArrayList<>();
                for (StocksEntity e : stockEntities) {
                    dtos.add(e.toDTO());
                }
                response.setData(new ServiceResponseObject(true, dtos));
                response.setStatusCode(200);
                return response;
            }
        } catch (ValidationException ex) {
            throw new ServiceException(ex.getMessage(), 400);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex.getMessage(), 400);

        }
        return null;
    }

}
