package com.project.businesslogic;

import com.project.businesslogic.user.AdminUser;
import com.project.businesslogic.user.User;

import javax.persistence.*;

/**
 * Клас сутевість. Відображення таблиці service_message у базі данних. У сервісного повідомлення є адміністратор,
 * що був закріпленій, робота, що викликала проблему, скаржник та виновник.
 */
@Entity
@Table(name = "service_message")
public class ServiceMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private AdminUser adminUser;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Job problemOrder;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private User victim;

    @ManyToOne(cascade = CascadeType.ALL)
    private User guilty;
    
    private String text;
    
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getGuilty() {
        return guilty;
    }

    public void setGuilty(User guilty) {
        this.guilty = guilty;
    }

    public User getVictim() {
        return victim;
    }

    public void setVictim(User victim) {
        this.victim = victim;
    }

    public Job getProblemOrder() {
        return problemOrder;
    }

    public void setProblemOrder(Job problemOrder) {
        this.problemOrder = problemOrder;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }
}
