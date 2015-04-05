package com.project.dao;

import com.project.businesslogic.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAO implements CRUD<User> {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.createQuery("from User where email = :email")
                .setParameter("email", email).uniqueResult();
        return user;
    }

    @Override
    public long create(User object) {
        return 0;
    }

    @Override
    @Transactional(readOnly = true)
    public User get(long id) {
        Session session = sessionFactory.getCurrentSession();
        return (User) session.get(User.class, id);
    }

    @Override
    public void update(User object) {

    }

    @Override
    public void delete(User object) {

    }
}
