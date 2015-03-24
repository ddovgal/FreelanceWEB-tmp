package com.project.businesslogic;

import com.project.businesslogic.user.CustomerUser;
import com.project.businesslogic.user.DeveloperUser;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String title;
    
    private String description;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;
    
    private double price;
    
    private boolean isFinished;
    
    private String agreement;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private DeveloperUser developerUser;

    @ManyToOne(cascade = CascadeType.ALL)
    private CustomerUser customerUser;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<DeveloperUser> applicants;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<DeveloperUser> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<DeveloperUser> applicants) {
        this.applicants = applicants;
    }

    public CustomerUser getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(CustomerUser customerUser) {
        this.customerUser = customerUser;
    }

    public DeveloperUser getDeveloperUser() {
        return developerUser;
    }

    public void setDeveloperUser(DeveloperUser developerUser) {
        this.developerUser = developerUser;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
