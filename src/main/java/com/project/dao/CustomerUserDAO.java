package com.project.dao;

import com.project.businesslogic.user.CustomerUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CustomerUserDAO implements CRUD<CustomerUser> {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long create(CustomerUser object) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(object);
        return object.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerUser get(long id) {
        Session session = sessionFactory.getCurrentSession();
        return (CustomerUser) session.load(CustomerUser.class, id);
    }

    @Override
    public void update(CustomerUser object) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(object);
    }

    @Override
    public void delete(CustomerUser object) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }
}
