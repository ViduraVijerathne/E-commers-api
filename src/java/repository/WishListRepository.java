/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import entity.UserEntity;
import entity.WishlistEntity;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author vidur
 */
public class WishListRepository extends Repository {
    
    public WishlistEntity get(int userID,int productID){
        session = getSession();
        Criteria criteria = session.createCriteria(WishlistEntity.class);
        criteria.add(Restrictions.eq("user.id",userID));
        criteria.add(Restrictions.eq("product.id", productID));
        WishlistEntity entity = (WishlistEntity) criteria.uniqueResult();
        return entity;
    }

    public WishlistEntity save(WishlistEntity entity) {
        session = getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(entity);
        transaction.commit();
        return entity;
    }
    
    public WishlistEntity remove(WishlistEntity entity){
        session = getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.delete(entity);
        transaction.commit();
        return entity;
    }
    
    public List<WishlistEntity> getAll(UserEntity user){
        session = getSession();
        Criteria criteria = session.createCriteria(WishlistEntity.class);
        criteria.add(Restrictions.eq("user.id",user.getId()));
        return criteria.list();
        
    }

}
