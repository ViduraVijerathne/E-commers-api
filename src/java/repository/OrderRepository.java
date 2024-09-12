/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import entity.OrderEntity;
import java.io.Serializable;
import org.hibernate.Transaction;

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

}
