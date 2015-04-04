package com.project.dao;

import com.project.businesslogic.user.CustomerUser;
import com.project.businesslogic.user.DeveloperUser;
import com.project.businesslogic.user.User;
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
        return (CustomerUser) session.get(CustomerUser.class, id);
    }

    @Transactional(readOnly = true)
    public CustomerUser getByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return (CustomerUser) session.createQuery("from CustomerUser d where d.email = :email")
                .setParameter("email", email).uniqueResult();
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

    //get default user with id 1, for getting his default image
    @Transactional(readOnly = true) //TODO: change to search by id
    public User getDefaultUser(){
        Session session = sessionFactory.getCurrentSession();
        return (User) session.createQuery("from User d where d.email = :email")
                .setParameter("email", "\"^_default$Email").uniqueResult();
    }
}
