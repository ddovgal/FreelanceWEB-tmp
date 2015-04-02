package com.project.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class BasicController {

    @RequestMapping("/home")
    public ModelAndView onIndex() {
        ModelAndView modelAndView = new ModelAndView("public/index");
        return modelAndView;
    }

    @RequestMapping("/p-redirect")
    public ModelAndView profileRedirect() {
//        CustomUserDetails userDetails = (CustomUserDetails) principal;
        ModelAndView modelAndView = null;
        if (hasRole("ROLE_DEVELOPER")) {
            System.out.println("Has role ROLE_DEVELOPER");
            modelAndView = new ModelAndView("redirect:/usr/developer/profile");
        }
        if (hasRole("ROLE_CUSTOMER")) {
            System.out.println("Has role ROLE_CUSTOMER");
            modelAndView = new ModelAndView("private/customer/profile");
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
