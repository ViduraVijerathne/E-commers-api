/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import org.hibernate.Session;

/**
 *
 * @author vidur
 */

abstract class Repository {
    Session session;

    public Repository() {
        session = config.HibernateUtil.getSessionFactory().openSession();
    }
    
    protected Session getSession(){
        session = config.HibernateUtil.getSessionFactory().openSession();
        return session;
    }
    
}
