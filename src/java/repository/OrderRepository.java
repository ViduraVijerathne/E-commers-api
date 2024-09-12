/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import entity.OrderEntity;
import entity.OrderItemEntity;
import entity.ShopEntity;
import entity.UserEntity;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author vidur
 */
public class OrderRepository extends Repository {

    public OrderEntity add(OrderEntity entity) {
        try {

            session = getSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            int id = (Integer) session.save(entity);
            entity.setId(id);
            transaction.commit();
            return entity;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public List<OrderEntity> get(UserEntity user) {
        session = getSession();
        Criteria criteria = session.createCriteria(OrderEntity.class);
        criteria.add(Restrictions.eq("user.id", user.getId()));
        criteria.addOrder(Order.desc("datetime"));
        return criteria.list();
    }

    public List<OrderItemEntity> get(ShopEntity shop) {
        session = getSession();
        Criteria criteria = session.createCriteria(OrderItemEntity.class, "orderItem");
//        criteria.add(Restrictions.eq("stocks.product.shop.id", shop.getId()));
        // Create aliases for navigating the associations
        criteria.createAlias("orderItem.stocks", "stocks");            // Alias for stocks
        criteria.createAlias("stocks.product", "product");             // Alias for product
        criteria.createAlias("product.shop", "shop");                  // Alias for shop

        // Add restriction to filter by shop ID
        criteria.add(Restrictions.eq("shop.id", shop.getId()));
        return criteria.list();
    }
}
