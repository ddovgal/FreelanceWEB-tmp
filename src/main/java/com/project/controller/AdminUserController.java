package com.project.controller;

import com.project.businesslogic.meta.UserType;
import com.project.businesslogic.user.AdminUser;
import com.project.businesslogic.user.DeveloperUser;
import com.project.dao.AdminUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usr/admin")
public class AdminUserController {

    private AdminUserDAO adminUserDAO;

    @Autowired
    public void setAdminUserDAO(AdminUserDAO adminUserDAO) {
        this.adminUserDAO = adminUserDAO;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute AdminUser adminUser) {
        adminUser.setUserType(UserType.ADMIN);
        System.out.println(adminUser);
        try {
            long id = adminUserDAO.create(adminUser);
            ModelAndView modelAndView =  new ModelAndView("public/success/on_register_success");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView =  new ModelAndView("public/admin_register");
            modelAndView.addObject("error", "Your attempt to register has failed! Please try again");
            return modelAndView;
        }
    }
}
