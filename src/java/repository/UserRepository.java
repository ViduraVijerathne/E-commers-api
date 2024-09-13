/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import dto.ServiceResponseObject;
import dto.UserDTO;
import entity.UserEntity;
import exceptions.ServiceException;
import java.util.List;
import java.util.UUID;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import services.Mail;

/**
 *
 * @author vidur
 */
public class UserRepository extends Repository<UserEntity> {

    public UserRepository() {
        super(UserEntity.class);
    }

    public UserEntity getByEmail(String email) {
        session = getSession();
        Criteria criteria1 = session.createCriteria(UserEntity.class);
        criteria1.add(Restrictions.eq("email", email));
        if (criteria1.list().isEmpty()) {
            return null;
        } else {
            return (UserEntity) criteria1.list().get(0);
        }
    }

    public UserEntity save(UserDTO user) {
        session = getSession();

        String vc = UUID.randomUUID().toString();
        UserEntity userEntity = user.toEntity();
        userEntity.setVc(vc);
        Transaction transaction = session.beginTransaction();
        session.save(userEntity);
        transaction.commit();
        return userEntity;
    }

    public void sendVCEmail(UserEntity entity) {
        String content = "<h1> your verifycation code is " + entity.getVc() + " </h1>";
        String subject = "Verification Email";
        Thread t1 = new Thread() {
            @Override
            public void run() {
                Mail.sendMail(entity.getEmail(), subject, content);
            }

        };
        t1.start();
    }

    public UserEntity getByEmailPassword(String email, String password) {
        session = getSession();

        Criteria criteria1 = session.createCriteria(UserEntity.class);
        criteria1.add(Restrictions.and(Restrictions.eq("email", email), Restrictions.eq("password", password)));
        List<UserEntity> entityList = criteria1.list();
        if (entityList.isEmpty()) {
            return null;
        } else {
            return entityList.get(0);
        }
    }

    public void update(UserEntity entity) throws ServiceException {
        session = getSession();

        UserEntity user = (UserEntity) session.load(UserEntity.class, entity.getId());
        if (user != null) {
            Transaction transaction = session.beginTransaction();

            user.setEmail(entity.getEmail());
            user.setFirstName(entity.getFirstName());
            user.setLastName(entity.getLastName());
            user.setVerified(entity.isVerified());
            user.setPassword(entity.getPassword());
            session.update(user);
            transaction.commit();
        } else {
            throw new ServiceException(new ServiceResponseObject(false, " user not found to update").toString(), 400);

        }
    }
}
