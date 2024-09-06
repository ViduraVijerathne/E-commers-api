/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import entity.CategoryEntity;

/**
 *
 * @author vidur
 */
public class CategoryRepository extends Repository {

    public CategoryEntity getById(int id) {
        session = getSession();
        CategoryEntity c =  (CategoryEntity) session.load(CategoryEntity.class, id);

        return c;

    }

}
