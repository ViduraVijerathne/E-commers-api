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
public class DistrictRepository extends Repository<DistrictEntity> {
    public DistrictRepository(){
        super(DistrictEntity.class);
    }
//    public List<DistrictEntity> getAll() {
//        session = getSession();
//        Criteria criteria = session.createCriteria(DistrictEntity.class);
//        return criteria.list();
//    }

//    public DistrictEntity get(int id) {
//        session = getSession();
//        DistrictEntity entity = (DistrictEntity) session.load(DistrictEntity.class, id);
//        return entity;
//    }

}
