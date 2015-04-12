package com.project.dao;

import com.project.businesslogic.Job;
import com.project.businesslogic.user.DeveloperUser;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * DAO для робіт. Містить усі методи для взаемодії з БД.
 */
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
        Criteria criteria = session.createCriteria(Job.class, "j");
        criteria.add(Restrictions.eq("j.id", id));
        criteria.setFetchMode("j.applicants", FetchMode.JOIN);
        criteria.setFetchMode("j.developerUser", FetchMode.JOIN);
        criteria.setFetchMode("j.customerUser", FetchMode.JOIN);
        return (Job) criteria.uniqueResult();
    }

    /**
     * Знаходить серед усіх робіт ті, що підлягають певному критерію.
     * @param title назва шуканої роботи
     * @param priceMin мінімальна ціна шуканої роботи
     * @param priceMax максимальна ціна шуканої роботи
     * @param tags теги шуканої роботи
     * @param firstResult обмеження пошуку - перший результат
     * @param maxResults обмеження пошуку - останній результат
     * @return список усіх знайдених робіт
     */
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
        criteria.add(Restrictions.eq("o.isFinished", false));
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

    /**
     * Повертає роботи по id її працівника.
     * @param id id її працівника
     * @param firstResult обмеження пошуку - перший результат
     * @param maxResults обмеження пошуку - останній результат
     * @return знайдені роботи
     */
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

    /**
     * Повертає роботи по id її претендента.
     * @param id id її претендента
     * @param firstResult обмеження пошуку - перший результат
     * @param maxResults обмеження пошуку - останній результат
     * @return знайдені роботи
     */
    @Transactional(readOnly = true)
    public List<Job> getByApplicant(long id, Integer firstResult, Integer maxResults) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Job.class, "j");
        criteria.createAlias("j.applicants", "appl");
//        criteria.createAlias("j.developerUser", "dev");
//        criteria.add(Restrictions.or(Restrictions.isNull("j.developerUser"), Restrictions.not(Restrictions.eq("dev.id", id))));
        criteria.add(Restrictions.eq("appl.id", id));


        if (firstResult != null)
            criteria.setFirstResult(firstResult);
        if (maxResults != null)
            criteria.setMaxResults(maxResults);
        return criteria.list();
    }

    /**
     * Повертає роботи по id її працівника.
     * @param id id її працівника
     * @param firstResult обмеження пошуку - перший результат
     * @param maxResults обмеження пошуку - останній результат
     * @return знайдені роботи
     */
    @Transactional(readOnly = true)
    public List<Job> getByWorker(long id, Integer firstResult, Integer maxResults) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Job.class, "j");
        criteria.createAlias("j.developerUser", "worker");
        criteria.add(Restrictions.eq("worker.id", id));

        if (firstResult != null)
            criteria.setFirstResult(firstResult);
        if (maxResults != null)
            criteria.setMaxResults(maxResults);
        return criteria.list();
    }

    /**
     * Змінюе статус роботи по її id.
     * @param id її id
     */
    @Transactional
    public void setFinished(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Job job = ((Job) session.get(Job.class, id));
        DeveloperUser developerUser = job.getDeveloperUser();
        if (developerUser!=null) developerUser.setRating(developerUser.getRating()+15);
        job.setFinished(true);
        job.setApplicants(null);
        update(job);
    }

    @Override
    public void update(Job object) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(object);
    }

    @Override
    public void delete(Job object) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }
}
