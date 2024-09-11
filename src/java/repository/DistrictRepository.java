/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import entity.DistrictEntity;
import java.util.List;
import org.hibernate.Criteria;

/**
 *
 * @author vidur
 */
public class DistrictRepository extends Repository{
    
    public List<DistrictEntity> get(){
        session = getSession();
        Criteria criteria = session.createCriteria(DistrictEntity.class);
        return criteria.list();
    }
    
}
