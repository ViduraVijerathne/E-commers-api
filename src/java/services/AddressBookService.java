/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dto.AddressBookDTO;
import dto.ServiceResponse;
import dto.ServiceResponseObject;
import dto.UserDTO;
import entity.AddressBookEntity;
import entity.DistrictEntity;
import entity.UserEntity;
import exceptions.ServiceException;
import exceptions.ValidationException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import repository.AddressBookRepository;
import repository.DistrictRepository;
import repository.UserRepository;

/**
 *
 * @author vidur
 */
public class AddressBookService implements Service {

    private AddressBookRepository addressBookRepository;
    private UserRepository userRepository;
    private DistrictRepository districtRepository;

    public AddressBookService() {
        this.addressBookRepository = new AddressBookRepository();
        this.userRepository = new UserRepository();
        this.districtRepository = new DistrictRepository();
    }

    public ServiceResponse add(AddressBookDTO dto) throws ServiceException {
        ServiceResponse response = new ServiceResponse();

        try {
            if (dto.isValidate()) {
                try {
                    UserEntity userEntity = userRepository.getByEmail(dto.getUser().getEmail());
                    DistrictEntity districtEntity = districtRepository.get(dto.getDistrict().getId());

                    AddressBookEntity entity = dto.toEntity();
                    entity.setDistrict(districtEntity);
                    entity.setUser(userEntity);

                    AddressBookEntity savedEntity = addressBookRepository.save(entity);
                    response.setData(new ServiceResponseObject(true, savedEntity.toDTO()));
                    response.setStatusCode(200);

                } catch (ObjectNotFoundException ex) {
                    throw new ServiceException(new ServiceResponseObject(false, " district not found").toString(), 400);

                } catch (NullPointerException ex) {
                    throw new ServiceException(new ServiceResponseObject(false, " district not found").toString(), 400);

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
            UserEntity entity = userRepository.getByEmail(dto.getEmail());
            if (entity != null) {
                List<AddressBookEntity> entities = addressBookRepository.get(dto);
                List<AddressBookDTO> dtos = new ArrayList<>();
                for (AddressBookEntity e : entities) {
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
