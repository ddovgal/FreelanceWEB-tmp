package com.project.dao;

import com.project.businesslogic.Job;
import com.project.businesslogic.user.CustomerUser;
import com.project.businesslogic.user.DeveloperUser;
import com.project.businesslogic.user.User;
import com.project.security.CustomUserDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO для замовника. Містить усі методи для взаемодії з БД.
 */
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

    /**
     * Отримуе замовника по його email.
     * @param email mail замовника
     * @return замовника по його email
     */
    @Transactional(readOnly = true)
    public CustomerUser getByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return (CustomerUser) session.createQuery("from CustomerUser d where d.email = :email")
                .setParameter("email", email).uniqueResult();
    }

    /**
     * Оновлюе замовника на основі нових данних.
     * @param realUserId id змінюваного замовника
     * @param tmpUser новий замовник з новими данними
     * @param customUserDetails об'єкт Spring security для корегування поточної сесії
     */
    @Transactional
    public void realUpdate(Long realUserId, CustomerUser tmpUser, CustomUserDetails customUserDetails){
        Session session = sessionFactory.getCurrentSession();
        CustomerUser realUser = (CustomerUser) session.get(CustomerUser.class, realUserId);
        realUser.setSnf(tmpUser.getSnf());
        customUserDetails.setSnf(tmpUser.getSnf());
        customUserDetails.setPassword(tmpUser.getPassword());
        realUser.setPassword(tmpUser.getPassword());
        realUser.setBirthday(tmpUser.getBirthday());
        if (tmpUser.getImage()!=null) {
            if (realUser.getImage()==null) realUser.setImage(tmpUser.getImage());
            else realUser.getImage().setImage(tmpUser.getImage().getImage());
        }
        session.merge(realUser);
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
