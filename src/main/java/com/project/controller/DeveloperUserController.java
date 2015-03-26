package com.project.controller;

import com.project.businesslogic.meta.UserType;
import com.project.businesslogic.user.DeveloperUser;
import com.project.dao.AdminUserDAO;
import com.project.dao.DeveloperUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usr/developer")
public class DeveloperUserController {

    private DeveloperUserDAO developerUserDAO;

    @Autowired
    public void setDeveloperUserDAO(DeveloperUserDAO developerUserDAO) {
        this.developerUserDAO = developerUserDAO;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute DeveloperUser developerUser) {
        developerUser.setUserType(UserType.DEVELOPER);
        System.out.println(developerUser);
        try {
            long id = developerUserDAO.create(developerUser);
            ModelAndView modelAndView =  new ModelAndView("/home");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView =  new ModelAndView("public/developer_register");
            modelAndView.addObject("error", "Your attempt to register has failed! Please try again");
            return modelAndView;
        }
    }


}
