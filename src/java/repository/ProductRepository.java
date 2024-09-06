/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import entity.ProductEntity;
import org.hibernate.Transaction;

/**
 *
 * @author vidur
 */
public class ProductRepository extends Repository {

    public ProductEntity Save(ProductEntity entity) {
        session = getSession();
        Transaction transaction = session.beginTransaction();
        int id = (Integer)session.save(entity);
        entity.setId(id);
        transaction.commit();

        return entity;
    }
}
