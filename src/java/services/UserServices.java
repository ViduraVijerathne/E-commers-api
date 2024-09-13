/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import config.HibernateUtil;
import dto.AuthDTO;
import dto.ServiceResponse;
import dto.ServiceResponseObject;
import dto.UserDTO;
import entity.UserEntity;
import exceptions.ServiceException;
import exceptions.ValidationException;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author vidur
 */
public class UserServices implements Service {

    private UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(UserServices.class);

    public UserServices() {
        this.userRepository = new UserRepository();
    }

    public ServiceResponse register(UserDTO user) throws ServiceException {
        ServiceResponse response = new ServiceResponse();

        try {
            if (user.isValidate()) {
                UserEntity e = userRepository.getByEmail(user.getEmail());
                if (e == null) {
                    UserEntity entity = userRepository.save(user.toEntity());
                    userRepository.sendVCEmail(entity);
                    response.setStatusCode(200);
                    response.setData(new ServiceResponseObject(true, "successfully created"));
                } else {
                    throw new ServiceException(new ServiceResponseObject(false, "user email alredy use by another user").toString(), 400);
                }

//                response.setData(user.toString());
            }
        } catch (ValidationException ex) {
            logger.info(ex.getMessage(), ex);
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 500);
        }

        return response;
    }

    public ServiceResponse login(AuthDTO auth, HttpSession httpSession) throws ServiceException {
        ServiceResponse response = new ServiceResponse();

        try {
            if (auth.isValidate()) {
                UserEntity entity = userRepository.getByEmailPassword(auth.getEmail(), auth.getPassword());
                if (entity != null) {
                    UserDTO dto = entity.toDTO();
                    response.setData(new ServiceResponseObject(true, dto.toString()));
                    if (dto.isVerified()) {
                        httpSession.setAttribute("user", dto);
                        response.setStatusCode(200);
                    } else {
                        response.setStatusCode(401);
                    }
                } else {
                    throw new ServiceException(new ServiceResponseObject(false, "invalid email or password").toString(), 400);
                }
            }
        } catch (ValidationException ex) {
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);
        }

        return response;
    }

    public ServiceResponse verification(AuthDTO auth, HttpSession httpSession) throws ServiceException {

        try {
            if (auth.isValidate() && auth.getVc() != null && !auth.getVc().isEmpty()) {

                UserEntity entity = userRepository.getByEmailPassword(auth.getEmail(), auth.getPassword());

                if (entity != null) {

                    if (entity.getVc().equals(auth.getVc())) {
                        entity.setVerified(true);
                        userRepository.update(entity);
                        return login(auth, httpSession);
                        
                    } else {
                        throw new ServiceException(new ServiceResponseObject(false, "invalid verification code").toString(), 400);

                    }

                } else {
                    throw new ServiceException(new ServiceResponseObject(false, "user not found").toString(), 400);

                }

            } else {
                throw new ValidationException("please enter verification code");
            }

        } catch (ValidationException ex) {
            throw new ServiceException(new ServiceResponseObject(false, ex.getMessage()).toString(), 400);
        }
    }
}
