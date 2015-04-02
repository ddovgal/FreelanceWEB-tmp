package com.project.controller;

import com.project.businesslogic.Job;
import com.project.businesslogic.meta.UserType;
import com.project.businesslogic.user.DeveloperUser;
import com.project.dao.AdminUserDAO;
import com.project.dao.DeveloperUserDAO;
import com.project.security.CustomUserDetails;
import com.project.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/usr/developer")
public class DeveloperUserController {

    private DeveloperUserDAO developerUserDAO;
    private JobService jobService;

    @Autowired
    public void setDeveloperUserDAO(DeveloperUserDAO developerUserDAO) {
        this.developerUserDAO = developerUserDAO;
    }

    @Autowired
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute DeveloperUser developerUser) {
        developerUser.setUserType(UserType.DEVELOPER);
        System.out.println(developerUser);
        try {
            long id = developerUserDAO.create(developerUser);
            ModelAndView modelAndView =  new ModelAndView("public/success/on_register_success");
            modelAndView.addObject("successMessage", "Developer user was successfully registered!");

            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView =  new ModelAndView("public/developer_register");
            modelAndView.addObject("error", "Your attempt to register has failed! Please try again");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView developerUserProfile(Principal principal) {
        try {
            String userEmail = principal.getName();
            DeveloperUser developerUser = developerUserDAO.getByEmail(userEmail);
            long developerId = developerUser.getId();
            List<Job> applicableJobs = jobService.getByApplicant(developerId, null, null);
            List<Job> currentJobs = jobService.getByWorker(developerId, null, null);


            ModelAndView modelAndView = new ModelAndView("private/developer/profile");
            modelAndView.addObject("applicableJobs", applicableJobs);
            modelAndView.addObject("currentJobs", currentJobs);
            modelAndView.addObject("developerUser", developerUser);
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("public/error/error");
            return modelAndView;
        }
    }


}
