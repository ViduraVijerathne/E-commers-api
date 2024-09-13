/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import entity.CategoryEntity;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;

/**
 *
 * @author vidur
 */
public class CategoryRepository extends Repository<CategoryEntity>{

    public CategoryRepository() {
        super(CategoryEntity.class);
    }
//
//    public List<CategoryEntity> getAll() {
//        List<CategoryEntity> list = new ArrayList<>();
//        session = getSession();
//        Criteria criteria = session.createCriteria(CategoryEntity.class);
//        list = criteria.list();
//        return list;
//    }

}
