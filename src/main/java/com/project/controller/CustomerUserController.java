package com.project.controller;

import com.project.businesslogic.Image;
import com.project.businesslogic.Job;
import com.project.businesslogic.meta.UserType;
import com.project.businesslogic.user.CustomerUser;
import com.project.dao.CustomerUserDAO;
import com.project.security.CustomUserDetails;
import com.project.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Клас - контролер. Саме в цьому класі виконуеться обробка запитів, та видача відповідей на запит. Данний контролер
 * оброблюе запити, потрібні для замовника, а токож усьго, з чим він трохи пов'язаний.
 */
@Controller
@RequestMapping("/usr/customer")
public class CustomerUserController {

    private CustomerUserDAO customerUserDAO;
    private JobService jobService;

    @Autowired
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    @Autowired
    public void setCustomerUserDAO(CustomerUserDAO customerUserDAO) {
        this.customerUserDAO = customerUserDAO;
    }

    /**
     * Редірект до редагування профілю замовника.
     * @param userId його id
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/profileDetail", method = RequestMethod.GET)
    public ModelAndView redirectToProfileDetail(@RequestParam(value = "userId") Long userId) {
        ModelAndView modelAndView = new ModelAndView("private/customer/profile_detail");
        CustomerUser customerUser = customerUserDAO.get(userId);
        modelAndView.addObject("user", customerUser);
        return modelAndView;
    }

    /**
     * Прийняття щойно відредагоаних данних замовника та їх оновлення.
     * @param tmpUser щойно відредагоані данні замовника
     * @param lastId id замовника
     * @param userImage оновлений аватар
     * @param principal об'єкт Spring security
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute CustomerUser tmpUser,
                             @RequestParam(value = "lastId") Long lastId,
                             @RequestParam(value = "userImage") MultipartFile userImage,
                             Principal principal) {
        try {
            if (userImage.getSize()!=0) {
                Image image = new Image();
                image.setImage(userImage.getBytes());
                tmpUser.setImage(image);
            }
            customerUserDAO.realUpdate(lastId, tmpUser, (CustomUserDetails) ((Authentication) principal).getPrincipal());

            ModelAndView modelAndView =  new ModelAndView("public/on_success");
            modelAndView.addObject("titleMessage", "Profile edit success");
            modelAndView.addObject("successMessage", "Your profile was successfully edited!");
            return modelAndView;
        } catch (IOException e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("public/error");
            return modelAndView;
        }
    }

    /**
     * Регістрація нового замовника.
     * @param customerUser щойно створені данні замовника
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute CustomerUser customerUser) {
        customerUser.setUserType(UserType.CUSTOMER);
        System.out.println(customerUser);
        try {
            long id = customerUserDAO.create(customerUser);
            ModelAndView modelAndView =  new ModelAndView("public/on_success");
            modelAndView.addObject("titleMessage", "Customer register success");
            modelAndView.addObject("successMessage", "Customer user was successfully registered!");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView =  new ModelAndView("public/customer_register");
            modelAndView.addObject("error", "Your attempt to register has failed! Please try again");
            return modelAndView;
        }
    }

    /**
     * Редірект до профілю замовника.
     * @param principal об'єкт Spring security
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView developerUserProfile(Principal principal) {
        try {
            String email = principal.getName();
            CustomerUser customerUser = customerUserDAO.getByEmail(email);
            long userId = customerUser.getId();
            List<Job> jobs = jobService.getByCustomerId(userId, 0, 20);
            ModelAndView modelAndView = new ModelAndView("private/customer/profile");
            modelAndView.addObject("jobs", jobs);
            modelAndView.addObject("userId", userId);
            modelAndView.addObject("customerUser", customerUser);
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("public/error");
            return modelAndView;
        }
    }

    /**
     * Редірект до повідомлень замовника.
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public ModelAndView redirectToMessages() {
        try {
            ModelAndView modelAndView = new ModelAndView("public/messages");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("public/error");
            return modelAndView;
        }
    }

}
