package com.project.dao;

import com.project.businesslogic.Job;
import com.project.businesslogic.user.DeveloperUser;
import com.project.businesslogic.user.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

    @Transactional(readOnly = true)
    public DeveloperUser getByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return (DeveloperUser) session.createQuery("from DeveloperUser d where d.email = :email")
                .setParameter("email", email).uniqueResult();
    }

    @Transactional(readOnly = true)
    public boolean isApplicableForJob(long jobId, String developerName) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Job.class, "j");
        criteria.createAlias("j.applicants", "appl");
        criteria.add(Restrictions.eq("appl.email", developerName));
        criteria.add(Restrictions.eq("j.id", jobId));
        Job j = (Job) criteria.uniqueResult();
        return j != null;
    }

    @Transactional(readOnly = true)
    public boolean isWorkerOfJob(long jobId, String developerName) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Job.class, "j");
        criteria.createAlias("j.developerUser", "w");
        criteria.add(Restrictions.eq("w.email", developerName));
        criteria.add(Restrictions.eq("j.id", jobId));
        Job j = (Job) criteria.uniqueResult();
        return j != null;
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

    //get default user with id 1, for getting his default image
    @Transactional(readOnly = true)  //TODO: change to search by id
    public User getDefaultUser(){
        Session session = sessionFactory.getCurrentSession();
        return (User) session.createQuery("from User d where d.email = :email")
                .setParameter("email", "\"^_default$Email").uniqueResult();
    }

}
