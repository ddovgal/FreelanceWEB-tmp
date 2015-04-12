package com.project.dao;

import com.project.businesslogic.user.AdminUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO для адміністратора. Містить усі методи для взаемодії з БД.
 */
@Repository
@Transactional
public class AdminUserDAO implements CRUD<AdminUser> {
    
    private SessionFactory sessionFactory;
    
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long create(AdminUser object) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(object);
        return object.getId();
    }


    @Override
    @Transactional(readOnly = true)
    public AdminUser get(long id) {
        Session session = sessionFactory.getCurrentSession();
        return (AdminUser) session.get(AdminUser.class, id);
    }

    @Override
    public void update(AdminUser object) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(object);
    }

    @Override
    public void delete(AdminUser object) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }
}
