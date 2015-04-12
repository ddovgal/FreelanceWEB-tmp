package com.project.security;

import com.project.businesslogic.user.User;
import com.project.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Клас Spring security, необхідний для опрацювання входу користувача.
 * На основі введених данних призначає поточному користувачу певний тип.
 */
public class CustomUserDetailsService implements UserDetailsService {

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> auths = new java.util.ArrayList<>();

        User user = userDAO.getByEmail(email);
        if (user != null) {
            switch (user.getUserType()) {
                case ADMIN: {
                    auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    break;
                }
                case DEVELOPER: {
                    auths.add(new SimpleGrantedAuthority("ROLE_DEVELOPER"));
                    break;
                }
                case CUSTOMER: {
                    auths.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
                    break;
                }
            }
            return new CustomUserDetails(auths,
                    user.getId(), user.getPassword(), user.getSnf(), user.getEmail() );

        }
        return null;
    }
}
