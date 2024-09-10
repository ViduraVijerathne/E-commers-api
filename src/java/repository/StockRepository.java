/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import entity.StocksEntity;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author vidur
 */
public class StockRepository extends Repository {

    public StocksEntity save(StocksEntity entity) {
        session = getSession();
        Transaction transaction = session.beginTransaction();
        int id = (Integer) session.save(entity);
        entity.setId(id);
        transaction.commit();
        return entity;
    }

    public List<StocksEntity> getByProductID(int pid) {
        session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(StocksEntity.class);
            criteria.add(Restrictions.eq("product.id", pid));
// Execute the query and fetch results
            List<StocksEntity> results = criteria.list();
            transaction.commit();
            return results;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

}
