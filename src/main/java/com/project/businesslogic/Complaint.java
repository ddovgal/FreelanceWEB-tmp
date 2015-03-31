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
    private Job problemJob;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Job getProblemJob() {
        return problemJob;
    }

    public void setProblemJob(Job problemJob) {
        this.problemJob = problemJob;
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
