package com.project.dao;

import com.project.businesslogic.Job;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class JobDAO implements CRUD<Job> {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long create(Job object) {
        Session session = sessionFactory.getCurrentSession();
        session.save(object);
        return object.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Job get(long id) {
        Session session = sessionFactory.getCurrentSession();
        return (Job) session.get(Job.class, id);
    }

    @Transactional(readOnly = true)
    public List<Job> getByCriterion(String title, Float priceMin, Float priceMax, String[] tags,
                                      Integer firstResult, Integer maxResults) {
        Session session = sessionFactory.getCurrentSession();

        System.out.println("title: " + title);
        System.out.println("priceMin: " + priceMin);
        System.out.println("priceMax: " + priceMax);
        System.out.println("tags: " + tags);
        System.out.println("firstResult: " + firstResult);
        System.out.println("maxResults: " + maxResults);

        Criteria criteria = session.createCriteria(Job.class, "o");
        if (title != null && !title.equals("")) {
            criteria.add(Restrictions.ilike("o.title", "%" + title + "%"));
        }
        if (priceMin != null) {
            criteria.add(Restrictions.gt("o.price", priceMin));
        }
        if (priceMax != null) {
            criteria.add(Restrictions.lt("o.price", priceMax));
        }
        if (tags != null) {
            String val = "";
            for (String tag : tags) {
                val += ("%" + tag + "%");
            }
            criteria.add(Restrictions.ilike("o.tags", val));
        }
        if (firstResult != null) {
            criteria.setFirstResult(firstResult);
        }
        if (maxResults != null) {
            criteria.setMaxResults(maxResults);
        }
        criteria.addOrder(org.hibernate.criterion.Order.desc("o.publishTime"));
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public List<Job> getByCustomerId(long id, Integer firstResult, Integer maxResults) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Job.class, "j");
        criteria.createAlias("j.customerUser", "cu");
        criteria.add(Restrictions.eq("cu.id", id));

        if (firstResult != null)
            criteria.setFirstResult(firstResult);
        if (maxResults != null)
            criteria.setMaxResults(maxResults);
        return criteria.list();
    }


    @Override
    public void update(Job object) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(object);
    }

    @Override
    public void delete(Job object) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }
}
