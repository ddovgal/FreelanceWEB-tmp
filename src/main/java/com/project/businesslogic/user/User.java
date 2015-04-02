package com.project.businesslogic.user;

import com.project.businesslogic.meta.UserType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    /**
     * Defines the type of user 
     */
    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String password;

    @Column(unique = true)
    private String email;
    private String snf;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    private double rating;

    @Lob
    private byte[] image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSnf() {
        return snf;
    }

    public void setSnf(String snf) {
        this.snf = snf;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userType=" + userType +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", snf='" + snf + '\'' +
                ", birthday=" + birthday +
                ", rating=" + rating +
                ", image='" + image + '\'' +
                '}';
    }
}
