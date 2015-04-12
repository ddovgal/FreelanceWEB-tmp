package com.project.businesslogic.user;


import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Клас типу користувача розробника. Розробник мае особливе для його типу користувача поле - здібності.
 */
@Entity
@Table(name = "developer_user")
public class DeveloperUser extends User {

    @Lob
    private String skills;


    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
