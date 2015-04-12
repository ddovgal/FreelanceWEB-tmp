package com.project.businesslogic;

import com.project.businesslogic.user.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Клас сутевість. Відображення таблиці dialog_message у базі данних. У діалогового повідомлення є
 * відправник, отримувач, дата відправлення та текст повідомлення.
 */
@Entity
@Table(name = "dialog_message")
public class DialogMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private User sender;

    @ManyToOne(cascade = CascadeType.ALL)
    private User receiver;
    
    private String text;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    
    private boolean isRead;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
