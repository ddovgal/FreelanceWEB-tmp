package com.project.dao;

import com.project.businesslogic.Image;
import com.project.businesslogic.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO для зображень-аватарів. Містить усі методи для взаемодії з БД.
 */
@Repository
@Transactional
public class ImageDAO implements CRUD<Image> {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long create(Image object) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(object);
        return object.getId();
    }

    @Override
    public Image get(long id) {
        Session session = sessionFactory.getCurrentSession();
        return (Image) session.get(Image.class, id);
    }

    /**
     * Пошук аватара користувача по його id.
     * @param userId id користувача
     * @return аватар користувача
     */
    public Image getByUserId(long userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, userId);
        return user.getImage();
    }

    /**
     * Пошук зображення по його назві (стовпець name у БД).
     * @param name назва зображення
     * @return знайдене зображення по його назві
     */
    public Image getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return (Image) session.createQuery("from Image im where im.name = :name")
                .setParameter("name", name)
                .uniqueResult();
    }

    @Override
    public void update(Image object) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(object);
    }

    @Override
    public void delete(Image object) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }
}
