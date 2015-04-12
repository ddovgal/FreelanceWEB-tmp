package com.project.businesslogic;

import com.project.businesslogic.user.CustomerUser;
import com.project.businesslogic.user.DeveloperUser;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Клас сутевість. Відображення таблиці job у базі данних. У роботи е заголовок, теги, опис, кінцевий срок,
 * дата публікації, плата, статус(завершена - закрита\не завершена - відкрита), замовник, та, можливо, розробник.
 */
@Entity
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String title;
    private String tags;
    @Lob
    private String description;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publishTime;
    
    private Float price;
    
    private boolean isFinished;

    @Lob
    private String agreement;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DeveloperUser developerUser;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CustomerUser customerUser;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "job_applicant",
            inverseJoinColumns = @JoinColumn(name = "applicant_id"),
            joinColumns = @JoinColumn(name = "job_id"))
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
