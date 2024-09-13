/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import entity.AddressBookEntity;
import entity.CartEntity;
import entity.MyEntity;
import entity.WishlistEntity;
import java.util.List;
import lombok.Setter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author vidur
 */
@Setter
abstract class Repository<E extends MyEntity> {

    Session session;
    private boolean isHaveMainPrimatyKey = true;
    private final Class<E> entityType;

    public Repository(Class<E> entityType) {
        this.entityType = entityType;
        session = config.HibernateUtil.getSessionFactory().openSession();
    }

    protected Session getSession() {
        session = config.HibernateUtil.getSessionFactory().openSession();
        return session;
    }

    public E save(E entity) {
//         session = getSession();
        Transaction transaction = session.beginTransaction();
        if (isHaveMainPrimatyKey) {
            int id = (Integer) session.save(entity);
            entity.setId(id);
        } else {
            session.save(entity);
        }
        transaction.commit();
        return entity;
    }

    public E get(int id) {
        E entity = (E) session.load(entityType, id);
        return entity;
    }

    public List<E> getAll() {
        Criteria criteria = session.createCriteria(entityType);
        return criteria.list();

    }

    public E remove(E entity) {
        session = getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.delete(entity);
        transaction.commit();
        return entity;
    }
    
    public E update(E entity) {
        session = getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.update(entity);
        transaction.commit();
        return entity;
    }

}
