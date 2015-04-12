package com.project.businesslogic.user;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Клас типу користувача замовника.
 */
@Entity
@Table(name = "customer_user")
public class CustomerUser extends User {
}
