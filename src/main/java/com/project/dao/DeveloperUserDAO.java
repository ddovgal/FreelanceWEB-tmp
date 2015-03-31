package com.project.dao;

import com.project.businesslogic.user.DeveloperUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DeveloperUserDAO implements CRUD<DeveloperUser> {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public long create(DeveloperUser object) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(object);
        return object.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public DeveloperUser get(long id) {
        Session session = sessionFactory.getCurrentSession();
        return (DeveloperUser) session.get(DeveloperUser.class, id);
    }

    @Override
    public void update(DeveloperUser object) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(object);
    }

    @Override
    public void delete(DeveloperUser object) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }
}
