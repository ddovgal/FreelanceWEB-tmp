package com.project.controller;

import com.project.businesslogic.Job;
import com.project.businesslogic.user.CustomerUser;
import com.project.dao.CustomerUserDAO;
import com.project.dao.JobDAO;
import com.project.security.CustomUserDetails;
import com.project.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;

    @Autowired
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    @RequestMapping(value = "/byId", method = RequestMethod.GET)
    public ModelAndView get(@RequestParam(value = "jobId") Long jobId) {
        try {
            Job job = jobService.get(jobId);
            ModelAndView modelAndView = new ModelAndView("public/job_obtions");
            modelAndView.addObject("job", job);
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("public/error/error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/byCriterion", method = RequestMethod.GET)
    public ModelAndView getJobsByCriterion(@RequestParam(value = "title", required = false) String title,
                                                @RequestParam(value = "priceMin", required = false) Float priceMin,
                                                @RequestParam(value = "priceMax", required = false) Float priceMax,
                                                @RequestParam(value = "tags", required = false) String tags,
                                                @RequestParam(value = "firstResult", required = false) Integer firstResult,
                                                @RequestParam(value = "maxResults", required = false) Integer maxResults) {
        try {
            String[] tagsArr = null;
            if (tags != null && !tags.equals("")) {
                tagsArr = tags.split("\\s");
            }

            List<Job> jobs = jobService.getByCriterion(title, priceMin, priceMax, tagsArr, firstResult, maxResults);
            ModelAndView modelAndView = new ModelAndView("public/jobs");
            modelAndView.addObject("jobs", jobs);
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("public/error/error");
            return modelAndView;

        }

    }

    @RequestMapping(value = "/byCustomer", method = RequestMethod.GET)
    public ModelAndView getJobsByCustomer(Principal principal) {
        long id = ((CustomUserDetails)principal).getId();
        ModelAndView modelAndView = new ModelAndView("public/jobs");

        return modelAndView;
    }

    @RequestMapping(value = "/newJob", method = RequestMethod.POST)
    public ModelAndView redirectToCreation() {
        ModelAndView modelAndView = new ModelAndView("private/customer/job_register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createJob(@ModelAttribute("job") Job job, @RequestParam("customerId") Integer customerId) {
        long id = jobService.create(job, customerId);

        ModelAndView modelAndView = new ModelAndView("public/success/on_register_success");
        modelAndView.addObject("successMessage", "New job was successfully registered!");
        return modelAndView;
    }
}
