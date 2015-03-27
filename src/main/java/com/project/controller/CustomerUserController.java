package com.project.controller;

import com.project.businesslogic.meta.UserType;
import com.project.businesslogic.user.CustomerUser;
import com.project.businesslogic.user.DeveloperUser;
import com.project.dao.CustomerUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usr/customer")
public class CustomerUserController {

    private CustomerUserDAO customerUserDAO;

    @Autowired
    public void setCustomerUserDAO(CustomerUserDAO customerUserDAO) {
        this.customerUserDAO = customerUserDAO;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute CustomerUser customerUser) {
        customerUser.setUserType(UserType.CUSTOMER);
        System.out.println(customerUser);
        try {
            long id = customerUserDAO.create(customerUser);
            ModelAndView modelAndView =  new ModelAndView("public/success/on_register_success");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView =  new ModelAndView("public/customer_register");
            modelAndView.addObject("error", "Your attempt to register has failed! Please try again");
            return modelAndView;
        }
    }
}
