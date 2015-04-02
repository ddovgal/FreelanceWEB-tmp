package com.project.controller;

import com.project.businesslogic.Job;
import com.project.businesslogic.user.User;
import com.project.dao.DeveloperUserDAO;
import com.project.dao.UserDAO;
import com.project.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;
    private DeveloperUserDAO developerUserDAO;
    private UserDAO userDAO;

    @Autowired
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    @Autowired
    public void setDeveloperUserDAO(DeveloperUserDAO developerUserDAO) {
        this.developerUserDAO = developerUserDAO;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @RequestMapping(value = "/options", method = RequestMethod.GET)
    public ModelAndView get(Principal principal, @RequestParam(value = "jobId") Long jobId) {
        try {
            Job job = jobService.get(jobId);
            ModelAndView modelAndView = new ModelAndView("public/job_obtions");
            modelAndView.addObject("job", job);

            if (principal != null) {
                String email = principal.getName();

                User user = userDAO.getByEmail(email);
                switch (user.getUserType()) {
                    case ADMIN: {
                        modelAndView.addObject("isAdmin", true);
                        break;
                    }
                    case CUSTOMER: {
                        modelAndView.addObject("isCustomer", true);
                        break;
                    }
                    case DEVELOPER: {
                        modelAndView.addObject("isDeveloper", true);
                        modelAndView.addObject("isApplicant", developerUserDAO.isApplicableForJob(jobId, email));
                        modelAndView.addObject("isWorker", developerUserDAO.isWorkerOfJob(jobId, email));
                        break;
                    }
                }
            }
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
                tagsArr = tags.split("[\\s;]");
            }

            List<Job> jobs = jobService.getByCriterion(title, priceMin, priceMax, tagsArr, firstResult, maxResults);
            for (Job job: jobs){
                try {
                    job.setDescription(job.getDescription().substring(0, 200));
                }catch (Exception e){}
            }
            ModelAndView modelAndView = new ModelAndView("public/jobs");
            modelAndView.addObject("jobs", jobs);
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("public/error/error");
            modelAndView.addObject(e);
            return modelAndView;
        }
    }

//    @RequestMapping(value = "/byCustomer", method = RequestMethod.GET)
//    public ModelAndView getJobsByCustomer(Principal principal) {
//        long id = ((CustomUserDetails)principal).getId();
//        ModelAndView modelAndView = new ModelAndView("public/jobs");
//
//        return modelAndView;
//    }

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

    @RequestMapping(value = "/add/applicant", method = RequestMethod.PUT)
    @ResponseBody
    public HttpStatus addApplicant(@RequestBody MultiValueMap<String,String> body) {
        try {
            Long jobId = Long.valueOf(body.getFirst("jobId"));
            Long devId = Long.valueOf(body.getFirst("devId"));
            jobService.addApplicant(jobId, devId);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.NOT_ACCEPTABLE;
        }
    }

    @RequestMapping(value = "/remove/applicant", method = RequestMethod.PUT)
    @ResponseBody
    public HttpStatus removeApplicant(@RequestBody MultiValueMap<String,String> body) {
        try {
            Long jobId = Long.valueOf(body.getFirst("jobId"));
            Long devId = Long.valueOf(body.getFirst("devId"));
            jobService.removeApplicant(jobId, devId);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.NOT_ACCEPTABLE;
        }
    }

}
