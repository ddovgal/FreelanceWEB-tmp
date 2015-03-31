package com.project.controller;

import com.project.businesslogic.Job;
import com.project.businesslogic.user.CustomerUser;
import com.project.dao.CustomerUserDAO;
import com.project.dao.JobDAO;
import com.project.security.CustomUserDetails;
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

    private JobDAO jobDAO;
    private CustomerUserDAO customerUserDAO;

    @Autowired
    public void setJobDAO(JobDAO jobDAO) {
        this.jobDAO = jobDAO;
    }

    @Autowired
    public void setCustomerUserDAO(CustomerUserDAO customerUserDAO) {
        this.customerUserDAO = customerUserDAO;
    }

    @RequestMapping(value = "/byCriterion", method = RequestMethod.GET)
         public ModelAndView getJobsByCriterion(@RequestParam(value = "title", required = false) String title,
                                                @RequestParam(value = "priceMin", required = false) Double priceMin,
                                                @RequestParam(value = "priceMax", required = false) Double priceMax,
                                                @RequestParam(value = "tags", required = false) String tags,
                                                @RequestParam(value = "firstResult", required = false) Integer firstResult,
                                                @RequestParam(value = "maxResults", required = false) Integer maxResults) {
        String[] tagsArr = null;
        if (tags != null) {
            tagsArr = tags.split("\\s,");
        }
        List<Job> orders = jobDAO.getByCriterion(title, priceMin, priceMax, tagsArr, firstResult, maxResults);
        ModelAndView modelAndView = new ModelAndView("public/jobs");
        modelAndView.addObject("orders", orders);
        return modelAndView;
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

        CustomerUser customerUser = customerUserDAO.get(customerId);
        job.setCustomerUser(customerUser);
        job.setPublishTime(new Date());
        long id = jobDAO.create(job);

        ModelAndView modelAndView = new ModelAndView("public/success/on_register_success");
        modelAndView.addObject("successMessage", "New job was successfully registered!");
        return modelAndView;
    }
}
