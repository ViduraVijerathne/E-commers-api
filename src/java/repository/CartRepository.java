/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import entity.CartEntity;
import entity.WishlistEntity;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author vidur
 */
public class CartRepository extends Repository<CartEntity> {

    public CartRepository() {
        super(CartEntity.class);
    }

    public CartEntity save(CartEntity entity) {
        session = getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(entity);
        transaction.commit();

        return entity;
    }

    public CartEntity get(int stockID, int userID) {
        session = getSession();
        Criteria criteria = session.createCriteria(CartEntity.class);
        criteria.add(Restrictions.eq("stocks.id", stockID));
        criteria.add(Restrictions.eq("user.id", userID));
        CartEntity entity = (CartEntity) criteria.uniqueResult();
        return entity;
    }

    public CartEntity update(CartEntity entity) {
        session = getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.update(entity);
        transaction.commit();
        return entity;
    }

    public List<CartEntity> getAll(int userID) {
        session = getSession();
        Criteria criteria = session.createCriteria(CartEntity.class);
        criteria.add(Restrictions.eq("user.id", userID));
        List<CartEntity> data = criteria.list();
        return data;
    }
    
    public CartEntity remove(CartEntity entity){
        session = getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.delete(entity);
        transaction.commit();
        return entity;
    }

}
