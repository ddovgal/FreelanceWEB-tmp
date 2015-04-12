package com.project.controller;

import com.project.businesslogic.meta.UserType;
import com.project.businesslogic.user.AdminUser;
import com.project.dao.AdminUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Клас - контролер. Саме в цьому класі виконуеться обробка запитів, та видача відповідей на запит. Данний контролер
 * оброблюе запити, потрібні для адміністратора, а токож усьго, з чим він трохи пов'язаний.
 */
@Controller
@RequestMapping("/usr/admin")
public class AdminUserController {

    private AdminUserDAO adminUserDAO;

    @Autowired
    public void setAdminUserDAO(AdminUserDAO adminUserDAO) {
        this.adminUserDAO = adminUserDAO;
    }

    /**
     * Регістрація нового адміністратора.
     * @param adminUser щойно створені данні адміністратора
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute AdminUser adminUser) {
        adminUser.setUserType(UserType.ADMIN);
        System.out.println(adminUser);
        try {
            long id = adminUserDAO.create(adminUser);
            ModelAndView modelAndView =  new ModelAndView("public/on_success");
            modelAndView.addObject("titleMessage", "Admin register success");
            modelAndView.addObject("successMessage", "Admin user was successfully registered!");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView =  new ModelAndView("public/admin_register");
            modelAndView.addObject("error", "Your attempt to register has failed! Please try again");
            return modelAndView;
        }
    }
}
