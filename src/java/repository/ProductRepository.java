/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import dto.Gender;
import entity.ProductEntity;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author vidur
 */
public class ProductRepository extends Repository<ProductEntity> {

    public ProductRepository() {
        super(ProductEntity.class);
    }

//    public ProductEntity save(ProductEntity entity) {
//        session = getSession();
//        Transaction transaction = session.beginTransaction();
//        int id = (Integer) session.save(entity);
//        entity.setId(id);
//        transaction.commit();
//        
//        return entity;
//    }
//    
    public ProductEntity getByProductID(int id) {
        session = getSession();
        ProductEntity entity = (ProductEntity) session.load(ProductEntity.class, id);
        return entity;
    }
    
    public List<ProductEntity> search(int pid, int shopid, String productName, int limit,
            int categoryId, Gender gender, double priceGreaterThan,
            double priceLowerThan) {
        
        session = getSession();
        Transaction transaction = session.beginTransaction();
        
        try {
            Criteria criteria = session.createCriteria(ProductEntity.class);

            // Add conditions based on the provided parameters
            if (pid != 0) {
                criteria.add(Restrictions.eq("id", pid));
            }
            if (shopid != 0) {
                criteria.add(Restrictions.eq("shop.id", shopid));
            }
            if (productName != null && !productName.trim().isEmpty()) {
                criteria.add(Restrictions.like("name", "%" + productName + "%"));
            }
            if (categoryId != 0) {
                criteria.add(Restrictions.eq("category.id", categoryId));
            }
            if (gender != null) {
                criteria.add(Restrictions.eq("gender", gender));
            }
            if (priceGreaterThan > 0) {
                criteria.add(Restrictions.ge("price", priceGreaterThan));
            }
            if (priceLowerThan > 0) {
                criteria.add(Restrictions.le("price", priceLowerThan));
            }

            // Set limit for the number of results
            if (limit > 0) {
                criteria.setMaxResults(limit);
            }

            // Execute the query and fetch results
            List<ProductEntity> results = criteria.list();
            transaction.commit();
            return results;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error executing product search.");
        }
        
    }
}
