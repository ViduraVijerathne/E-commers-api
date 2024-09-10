/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import entity.CartEntity;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author vidur
 */
public class CartRepository extends Repository {

    public CartEntity save(CartEntity entity) {
        System.out.println(entity.getUserId());
        System.out.println(entity.getStocksId());
        System.out.println(entity.getCartQty());

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
    
    public CartEntity update(CartEntity entity){
        session  = getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.update(entity);
        transaction.commit();
        return  entity;
    }

}
