package com.project.services;

import com.project.businesslogic.Job;
import com.project.businesslogic.user.CustomerUser;
import com.project.businesslogic.user.DeveloperUser;
import com.project.dao.CustomerUserDAO;
import com.project.dao.DeveloperUserDAO;
import com.project.dao.JobDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class JobService {

    private JobDAO jobDAO;
    private CustomerUserDAO customerUserDAO;
    private DeveloperUserDAO developerUserDAO;

    @Autowired
    public void setJobDAO(JobDAO jobDAO) {
        this.jobDAO = jobDAO;
    }

    @Autowired
    public void setCustomerUserDAO(CustomerUserDAO customerUserDAO) {
        this.customerUserDAO = customerUserDAO;
    }

    @Autowired
    public void setDeveloperUserDAO(DeveloperUserDAO developerUserDAO) {
        this.developerUserDAO = developerUserDAO;
    }

    public long create(Job object, Long customerId) {
        CustomerUser customerUser = customerUserDAO.get(customerId);
        object.setCustomerUser(customerUser);
        //setting publish time
        object.setPublishTime(new Date());
        return jobDAO.create(object);
    }

    @Transactional(readOnly = true)
    public Job get(long id) {
        Job job = jobDAO.get(id);
        job.getApplicants();
        job.getDeveloperUser();
        job.getCustomerUser();
        return job;
    }

    @Transactional(readOnly = true)
    public List<Job> getByCriterion(String title, Float priceMin, Float priceMax, String[] tags,
                                    Integer firstResult, Integer maxResults) {
        return jobDAO.getByCriterion(title, priceMin, priceMax, tags, firstResult, maxResults);
    }

    @Transactional(readOnly = true)
    public List<Job> getByCustomerId(long id, Integer firstResult, Integer maxResults) {
        return jobDAO.getByCustomerId(id, firstResult, maxResults);
    }

    @Transactional(readOnly = true)
    public List<Job> getByApplicant(long id, Integer firstResult, Integer maxResults) {
        return jobDAO.getByApplicant(id, firstResult, maxResults);
    }

    @Transactional(readOnly = true)
    public List<Job> getByWorker(long id, Integer firstResult, Integer maxResults) {
        return jobDAO.getByWorker(id, firstResult, maxResults);
    }

    public void addDeveloper(long jobId, long devId) {
        DeveloperUser developerUser = developerUserDAO.get(devId);
        Job job = jobDAO.get(jobId);
        job.setDeveloperUser(developerUser);
        jobDAO.update(job);
    }

    public void removeDeveloper(long jobId, long devId) {
        DeveloperUser developerUser = developerUserDAO.get(devId);
        developerUser.setRating(developerUser.getRating()-5);
        Job job = jobDAO.get(jobId);
        job.setDeveloperUser(null);
        jobDAO.update(job);
    }

    public void addApplicant(long jobId, long devId) {
        DeveloperUser developerUser = developerUserDAO.get(devId);
        Job job = jobDAO.get(jobId);
        job.getApplicants().add(developerUser);
        jobDAO.update(job);
    }

    public void removeApplicant(long jobId, long devId) {
        DeveloperUser developerUser = developerUserDAO.get(devId);
        Job job = jobDAO.get(jobId);
        job.getApplicants().remove(developerUser);
        jobDAO.update(job);
    }

    public void realUpdate(Long realJobId, Job tmpJob){
        Job realJob = jobDAO.get(realJobId);
        realJob.setTitle(tmpJob.getTitle());
        realJob.setTags(tmpJob.getTags());
        realJob.setDescription(tmpJob.getDescription());
        realJob.setPrice(tmpJob.getPrice());
        realJob.setAgreement(tmpJob.getAgreement());
        jobDAO.update(realJob);
    }

    public void setFinished(Long id) {
        jobDAO.setFinished(id);
    }

    public void update(Job object) {
        jobDAO.update(object);
    }

    public void delete(Job object) {
        jobDAO.delete(object);
    }
}
