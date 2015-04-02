package com.project.controller;

import com.project.businesslogic.Job;
import com.project.dao.CustomerUserDAO;
import com.project.dao.UserDAO;
import com.project.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
public class BasicController {

    private JobService jobService;
    private UserDAO userDAO;
    private CustomerUserDAO customerUserDAO;

    @Autowired
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setCustomerUserDAO(CustomerUserDAO customerUserDAO) {
        this.customerUserDAO = customerUserDAO;
    }

    @RequestMapping("/home")
    public ModelAndView onIndex() {
        ModelAndView modelAndView = new ModelAndView("public/index");
        return modelAndView;
    }

    @RequestMapping("/p-redirect")
    public ModelAndView profileRedirect(Principal principal) {
        ModelAndView modelAndView = null;
        if (hasRole("ROLE_DEVELOPER")) {
            System.out.println("Has role ROLE_DEVELOPER");
            modelAndView = new ModelAndView("private/developer/profile");
        }
        if (hasRole("ROLE_CUSTOMER")) {
            System.out.println("Has role ROLE_CUSTOMER");
            String email = principal.getName();
            Long userId = userDAO.getByEmail(email).getId();
            List<Job> jobs = jobService.getByCustomerId(userId, 0, 20);
            modelAndView = new ModelAndView("private/customer/profile");
            modelAndView.addObject("jobs", jobs);
            modelAndView.addObject("userId", userId);
        }
        if (hasRole("ROLE_ADMIN")) {
            System.out.println("Has role ROLE_ADMIN");
            modelAndView = new ModelAndView("private/admin/profile");
        }
        return modelAndView;
    }

    private boolean hasRole(String role) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals(role);
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }

}
