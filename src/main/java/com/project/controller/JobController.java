package com.project.controller;

import com.project.businesslogic.Job;
import com.project.businesslogic.user.CustomerUser;
import com.project.businesslogic.user.DeveloperUser;
import com.project.businesslogic.user.User;
import com.project.dao.DeveloperUserDAO;
import com.project.dao.UserDAO;
import com.project.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

/**
 * Клас - контролер. Саме в цьому класі виконуеться обробка запитів, та видача відповідей на запит. Данний контролер
 * оброблюе запити, пов'язані з роботами.
 */
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

    /**
     * Редірект до сторінки детальної інформації данної роботи.
     * @param principal об'єкт Spring security
     * @param jobId робота, яку детально треба продивитися
     * @return модель-представлення сторінки
     */
    @Transactional
    @RequestMapping(value = "/options", method = RequestMethod.GET)
    public ModelAndView get(Principal principal, @RequestParam(value = "jobId") Long jobId) {
        try {
            Job job = jobService.get(jobId);
            ModelAndView modelAndView = new ModelAndView("public/job_options");
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
                        modelAndView.addObject("isOpen", job.getDeveloperUser()==null);
                        modelAndView.addObject("isMine", job.getCustomerUser().getId()==user.getId());
                        modelAndView.addObject("jobId", job.getId());
                        DeveloperUser developerUser = job.getDeveloperUser();
                        if (developerUser!=null) System.out.println(developerUser.getSnf());
                        modelAndView.addObject("developerUser", developerUser);
                        List<DeveloperUser> appl = job.getApplicants();
                        modelAndView.addObject("applicants", appl);
                        if (appl.size()!=0){
                            for (DeveloperUser d : appl) {
                                System.out.println(appl);
                            }
                        }
                        break;
                    }
                    case DEVELOPER: {
                        modelAndView.addObject("isDeveloper", true);
                        modelAndView.addObject("isApplicant", developerUserDAO.isApplicableForJob(jobId, email));
                        modelAndView.addObject("isWorker", developerUserDAO.isWorkerOfJob(jobId, email));
                        CustomerUser customerUser = job.getCustomerUser();
                        System.out.println(customerUser.getSnf());
                        modelAndView.addObject("customerUser", customerUser);
                        break;
                    }
                }
            }
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("public/error");
            return modelAndView;
        }
    }

    /**
     * Вибір серед усіх робіт лише ті, що підпадають під запит.
     * @param title назва шуканої роботи
     * @param priceMin мінімальна ціна шуканої роботи
     * @param priceMax максимальна ціна шуканої роботи
     * @param tags теги шуканої роботи
     * @param firstResult обмеження пошуку - перший результат
     * @param maxResults обмеження пошуку - останній результат
     * @return модель-представлення сторінки
     */
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
            ModelAndView modelAndView = new ModelAndView("public/error");
            modelAndView.addObject(e);
            return modelAndView;
        }
    }

    /**
     * Редірект до сторінки створення нової роботи.
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/newJob", method = RequestMethod.GET)
    public ModelAndView redirectToCreation() {
        ModelAndView modelAndView = new ModelAndView("private/customer/job_register-edit");
        modelAndView.addObject("isToCreate", true);
        return modelAndView;
    }

    /**
     * Редірект до сторінки редагуваення певної роботи.
     * @param jobId id роботи, що необхідно редагувати
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/editJob", method = RequestMethod.GET)
    public ModelAndView redirectToEdit(@RequestParam(value = "jobId") Long jobId) {
        ModelAndView modelAndView = new ModelAndView("private/customer/job_register-edit");
        Job job = jobService.get(jobId);
        modelAndView.addObject("isToCreate", false);
        modelAndView.addObject("jobObj", job);
        return modelAndView;
    }

    /**
     * Видалення роботи по її id.
     * @param body об'єкт, що несе усі данні
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/deleteJob", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView deleteJob(@RequestBody MultiValueMap<String,String> body) {
        Long jobId = Long.valueOf(body.getFirst("jobId"));
        Job job = jobService.get(jobId);
        job.setCustomerUser(null);
        job.setDeveloperUser(null);
        job.setApplicants(null);
        jobService.delete(job);

        ModelAndView modelAndView = new ModelAndView("public/on_success");
        modelAndView.addObject("titleMessage", "Job delete success");
        modelAndView.addObject("successMessage", "Job was successfully deleted!");
        return modelAndView;
    }

    /**
     * Редірект до сторінки створення певної роботи.
     * @param job щойно створена\відредагована робота
     * @param customerId id замовника
     * @param isToCreate чи на створення прийшов запит
     * @param lastId id редагуемої роботи(для редагування)
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createJob(@ModelAttribute("job") Job job,
                                  @RequestParam(value = "customerId") Long customerId,
                                  @RequestParam(value = "isToCreate") boolean isToCreate,
                                  @RequestParam(value = "lastId") Long lastId) {
        ModelAndView modelAndView = new ModelAndView("public/on_success");
        if (isToCreate){
            long id = jobService.create(job, customerId);

            modelAndView.addObject("titleMessage", "Job register success");
            modelAndView.addObject("successMessage", "New job was successfully registered!");
        } else {
            jobService.realUpdate(lastId, job);

            modelAndView.addObject("titleMessage", "Job edit success");
            modelAndView.addObject("successMessage", "Job was successfully edited!");
        }
        return modelAndView;
    }

    /**
     * Зміна статусу данної роботи на виконано
     * @param jobId id роботі, яку необхідно закрити
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/setFinished", method = RequestMethod.POST)
    public ModelAndView setFinished(@RequestParam(value = "jobId") Long jobId){
        ModelAndView modelAndView = new ModelAndView("public/on_success");
        jobService.setFinished(jobId);
        modelAndView.addObject("titleMessage", "Job finished");
        modelAndView.addObject("successMessage", "Job status was successfully changed to finished!");
        return  modelAndView;
    }

    /**
     * Визначення данного розробника виконавцем данної роботи.
     * @param jobId id роботи
     * @param developerId id розробника
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/add/developer", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addDeveloper(@RequestParam Long jobId, @RequestParam Long developerId) {
        try {
            jobService.addDeveloper(jobId, developerId);
            jobService.removeApplicant(jobId, developerId);
            ModelAndView modelAndView = new ModelAndView("public/on_success");
            modelAndView.addObject("titleMessage", "Applicant accept");
            modelAndView.addObject("successMessage", "Applicant was successfully chosen for this job");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("public/error");
            return modelAndView;
        }
    }

    /**
     * Звільнення данного розробника з поста виконавця данної роботи.
     * @param jobId id роботи
     * @param developerId id розробника
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/remove/developer", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView removeDeveloper(@RequestParam Long jobId, @RequestParam Long developerId) {
        try {
            jobService.removeDeveloper(jobId, developerId);
            ModelAndView modelAndView = new ModelAndView("public/on_success");
            modelAndView.addObject("titleMessage", "Developer dismissed");
            modelAndView.addObject("successMessage", "This bad developer was successfully dismissed");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("public/error");
            return modelAndView;
        }
    }

    /**
     * Доповнення списку претендентів данної роботи новим претендентом.
     * @param body об'єкт, що несе усі данні
     * @return модель-представлення сторінки
     */
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

    /**
     * Видалення зі списку претендентів данної роботи данного претенденту.
     * @param body об'єкт, що несе усі данні
     * @return модель-представлення сторінки
     */
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
