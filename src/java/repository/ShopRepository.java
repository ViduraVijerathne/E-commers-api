/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import dto.ShopDTO;
import entity.ShopEntity;
import entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author vidur
 */
public class ShopRepository extends Repository {

    public ArrayList<ShopEntity> getByUserId(int id) {
        System.out.println("SHOP GET ID : "+id);
        session = getSession();
        try {
            Criteria criteria = session.createCriteria(ShopEntity.class);
            criteria.createAlias("user", "u"); // Create an alias for the user
            criteria.add(Restrictions.eq("u.id", id));
            ArrayList<ShopEntity> entity = (ArrayList)criteria.list();
            System.out.println(entity.size());
            return entity; 
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }

    }

    public ShopEntity save(ShopDTO dto) {
        
       
        session = getSession();
        Transaction transaction = session.beginTransaction();
        Integer id = (Integer)session.save(dto.toEntity());
        transaction.commit();
        dto.setId(id);
        return dto.toEntity();
    }
}
