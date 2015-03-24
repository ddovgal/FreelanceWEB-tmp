package com.project.businesslogic;

import com.project.businesslogic.user.User;

import javax.persistence.*;

@Entity
@Table(name = "complaint")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    private User user;
    
    private String text;
    
    @ManyToOne
    private Order problemOrder;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order getProblemOrder() {
        return problemOrder;
    }

    public void setProblemOrder(Order problemOrder) {
        this.problemOrder = problemOrder;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
