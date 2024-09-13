/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import entity.StocksEntity;
import entity.WishlistEntity;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author vidur
 */
public class StockRepository extends Repository<StocksEntity> {

    public StockRepository() {
        super(StocksEntity.class);
    }

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

    public StocksEntity get(int stockID) {
        session = getSession();
        StocksEntity entity = (StocksEntity) session.get(StocksEntity.class, stockID);
        return entity;
    }

    synchronized public void substractStock(int stockID, int qty) {
    session = getSession();
    Transaction transaction = session.beginTransaction();
    try {
        // Fetch the StocksEntity by stockID
        StocksEntity stock = (StocksEntity) session.get(StocksEntity.class, stockID);
        
        if (stock != null) {
            // Check if enough stock is available
            int currentStock = stock.getQuantity();
            if (currentStock >= qty) {
                // Subtract the qty from the stock
                stock.setQuantity(currentStock - qty);
                
                // Update the stock entity in the database
                session.update(stock);
                transaction.commit();
            } else {
                // If there is not enough stock, throw an exception
                throw new IllegalArgumentException("Not enough stock available.");
            }
        } else {
            throw new IllegalArgumentException("Stock with ID " + stockID + " not found.");
        }
    } catch (Exception ex) {
        if (transaction != null) {
            transaction.rollback();  // Rollback in case of an error
        }
        ex.printStackTrace();
        throw ex;  // Rethrow the exception after logging it
    }
}


}
