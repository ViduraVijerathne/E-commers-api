/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import dto.UserDTO;
import entity.AddressBookEntity;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author vidur
 */
public class AddressBookRepository extends Repository {

    public AddressBookEntity save(AddressBookEntity entity) {
        session = getSession();
        Transaction transaction = session.beginTransaction();
        int id = (Integer) session.save(entity);
        entity.setId(id);
        transaction.commit();
        return entity;
    }

    public List<AddressBookEntity> get(UserDTO user) {
        session = getSession();
        Criteria criteria = session.createCriteria(AddressBookEntity.class);
        criteria.add(Restrictions.eq("user.id", user.getId()));
        List<AddressBookEntity> entities = criteria.list();
        return entities;
    }

}
