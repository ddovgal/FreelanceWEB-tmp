package com.project.businesslogic.user;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Клас типу користувача адміністратора.
 */
@Entity
@Table(name = "admin_user")
public class AdminUser extends User {
}
