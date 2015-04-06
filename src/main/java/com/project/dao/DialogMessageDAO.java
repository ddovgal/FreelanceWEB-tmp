package com.project.dao;

import com.project.businesslogic.DialogMessage;
import com.project.businesslogic.user.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional
public class DialogMessageDAO implements CRUD<DialogMessage>{

    private SessionFactory sessionFactory;
    private UserDAO userDAO;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public long create(DialogMessage object) {
        Session session = sessionFactory.getCurrentSession();
        session.save(object);
        return object.getId();
    }

    @Transactional
    public long realCreate(Long senderId, Long receiverId, String text){
        Session session = sessionFactory.getCurrentSession();
        DialogMessage dialogMessage = new DialogMessage();
        User sender = userDAO.get(senderId);
        User receiver = userDAO.get(receiverId);
        dialogMessage.setSender(sender);
        dialogMessage.setReceiver(receiver);
        dialogMessage.setText(text);
        dialogMessage.setTime(new Date());
        session.save(dialogMessage);
        return dialogMessage.getId();
    }

    @Override
    public DialogMessage get(long id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(DialogMessage.class, "dm");
        criteria.add(Restrictions.eq("dm.id", id));
        return (DialogMessage) criteria.uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<DialogMessage> getByUserId(Long userId, Integer firstResult, Integer maxResults){
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(DialogMessage.class, "dm");
        criteria.createAlias("dm.sender", "sender");
        criteria.createAlias("dm.receiver", "receiver");
        Criterion senderCriteria = Restrictions.eq("sender.id", userId);
        Criterion receiverCriteria = Restrictions.eq("receiver.id", userId);
        criteria.add(Restrictions.or(senderCriteria, receiverCriteria));

        if (firstResult != null)
            criteria.setFirstResult(firstResult);
        if (maxResults != null)
            criteria.setMaxResults(maxResults);
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public List<User> getAllInterlocutors(Long myId, Integer firstResult, Integer maxResults){
        Session session = sessionFactory.getCurrentSession();
        List<DialogMessage> messagesList = getByUserId(myId, firstResult, maxResults);
        Set<User> interlocutorsSet = new HashSet<>();

        for (DialogMessage message: messagesList){
            if (message.getSender().getId()!=myId) interlocutorsSet.add(message.getSender());
            else interlocutorsSet.add(message.getReceiver());
        }
        List<User> resultList = new ArrayList<User>(interlocutorsSet);
        return resultList;
    }

    @Transactional(readOnly = true)
    public List<DialogMessage> getMessagesForInterlocutor(Long myId, Long interlocutorId, Integer firstResult, Integer maxResults){
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(DialogMessage.class, "dm");
        criteria.createAlias("dm.sender", "sender");
        criteria.createAlias("dm.receiver", "receiver");
        Criterion senderCriteria = Restrictions.and(Restrictions.eq("sender.id", interlocutorId), Restrictions.eq("receiver.id", myId));
        Criterion receiverCriteria = Restrictions.and(Restrictions.eq("sender.id", myId), Restrictions.eq("receiver.id", interlocutorId));
        criteria.add(Restrictions.or(senderCriteria, receiverCriteria));

        if (firstResult != null)
            criteria.setFirstResult(firstResult);
        if (maxResults != null)
            criteria.setMaxResults(maxResults);

        List<DialogMessage> resultList = criteria.list();
        Collections.sort(resultList, new Comparator<DialogMessage>() {
            @Override
            public int compare(DialogMessage o1, DialogMessage o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });

        return resultList;
    }

    @Override
    public void update(DialogMessage object) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(object);
    }

    @Override
    public void delete(DialogMessage object) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }

}
