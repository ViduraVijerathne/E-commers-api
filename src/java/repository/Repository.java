/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import entity.MyEntity;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author vidur
 */
@Setter
abstract class Repository<E extends MyEntity> {

    Session session;
    private boolean isHaveMainPrimatyKey = true;

    public Repository() {
        session = config.HibernateUtil.getSessionFactory().openSession();
    }

    protected Session getSession() {
        session = config.HibernateUtil.getSessionFactory().openSession();
        return session;
    }

    public E save(E entity) {
//         session = getSession();
        Transaction transaction = session.beginTransaction();
        if (isHaveMainPrimatyKey) {
            int id = (Integer) session.save(entity);
            entity.setId(id);
        } else {
            session.save(entity);
        }
        transaction.commit();
        return entity;
    }

}
