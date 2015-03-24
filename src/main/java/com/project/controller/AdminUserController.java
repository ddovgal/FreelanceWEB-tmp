package com.project.controller;

import com.project.dao.AdminUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adminUsers")
public class AdminUserController {
    
    private AdminUserDAO adminUserDAO;

    @Autowired
    public void setAdminUserDAO(AdminUserDAO adminUserDAO) {
        this.adminUserDAO = adminUserDAO;
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public ModelAndView create() {
//
//    }
}
