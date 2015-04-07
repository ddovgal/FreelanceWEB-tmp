package com.project.controller;

import com.project.businesslogic.Image;
import com.project.businesslogic.Job;
import com.project.businesslogic.meta.UserType;
import com.project.businesslogic.user.DeveloperUser;
import com.project.dao.DeveloperUserDAO;
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

    @RequestMapping(value = "/profileDetail", method = RequestMethod.GET)
    public ModelAndView redirectToProfileDetail(@RequestParam(value = "userId") Long userId) {
        ModelAndView modelAndView = new ModelAndView("private/developer/profile_detail");
        DeveloperUser developerUser = developerUserDAO.get(userId);
        modelAndView.addObject("user", developerUser);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute DeveloperUser tmpUser,
                             @RequestParam(value = "lastId") Long lastId,
                             @RequestParam(value = "userImage") MultipartFile userImage,
                             Principal principal) {
        try {
            if (userImage.getSize()!=0) {
                Image image = new Image();
                image.setImage(userImage.getBytes());
                tmpUser.setImage(image);
            }
            developerUserDAO.realUpdate(lastId, tmpUser, (CustomUserDetails) ((Authentication) principal).getPrincipal());

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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute DeveloperUser developerUser) {
        developerUser.setUserType(UserType.DEVELOPER);
        System.out.println(developerUser);
        try {
            long id = developerUserDAO.create(developerUser);
            ModelAndView modelAndView =  new ModelAndView("public/on_success");
            modelAndView.addObject("titleMessage", "Developer register success");
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
            ModelAndView modelAndView = new ModelAndView("public/error");
            return modelAndView;
        }
    }
}
