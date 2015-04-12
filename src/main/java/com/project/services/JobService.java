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

/**
 * Клас - сервіс. Більш високорівневе DAO. Містить усі методи, необхідні для реалізування логіки операцій
 * у контролерах, пов'язаних з роботою.
 */
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

    /**
     * Створюэ роботу на основі обекту роботі та id замовника.
     * @param object робота, яку буде збережено
     * @param customerId id замовника.
     * @return id щойно створеної роботи
     */
    public long create(Job object, Long customerId) {
        CustomerUser customerUser = customerUserDAO.get(customerId);
        object.setCustomerUser(customerUser);
        //setting publish time
        object.setPublishTime(new Date());
        return jobDAO.create(object);
    }

    /**
     * Повертає роботу по її id.
     * @param id id роботи
     * @return роботу по її id
     */
    @Transactional(readOnly = true)
    public Job get(long id) {
        Job job = jobDAO.get(id);
        job.getApplicants();
        job.getDeveloperUser();
        job.getCustomerUser();
        return job;
    }

    /**
     * Повертає роботу по її критерію.
     * @param title заголовок
     * @param priceMin мінімальна ціна роботи
     * @param priceMax максимальна ціна роботи
     * @param tags теги, що е у шуканій роботі
     * @param firstResult обмеження пошуку - перший результат
     * @param maxResults обмеження пошуку - останній результат
     * @return знайдену роботу
     */
    @Transactional(readOnly = true)
    public List<Job> getByCriterion(String title, Float priceMin, Float priceMax, String[] tags,
                                    Integer firstResult, Integer maxResults) {
        return jobDAO.getByCriterion(title, priceMin, priceMax, tags, firstResult, maxResults);
    }

    /**
     * Повертає роботи по id її замовника.
     * @param id id її замовника
     * @param firstResult обмеження пошуку - перший результат
     * @param maxResults обмеження пошуку - останній результат
     * @return знайдені роботи
     */
    @Transactional(readOnly = true)
    public List<Job> getByCustomerId(long id, Integer firstResult, Integer maxResults) {
        return jobDAO.getByCustomerId(id, firstResult, maxResults);
    }

    /**
     * Повертає роботи по id її претендента.
     * @param id id її претендента
     * @param firstResult обмеження пошуку - перший результат
     * @param maxResults обмеження пошуку - останній результат
     * @return знайдені роботи
     */
    @Transactional(readOnly = true)
    public List<Job> getByApplicant(long id, Integer firstResult, Integer maxResults) {
        return jobDAO.getByApplicant(id, firstResult, maxResults);
    }

    /**
     * Повертає роботи по id її працівника.
     * @param id id її працівника
     * @param firstResult обмеження пошуку - перший результат
     * @param maxResults обмеження пошуку - останній результат
     * @return знайдені роботи
     */
    @Transactional(readOnly = true)
    public List<Job> getByWorker(long id, Integer firstResult, Integer maxResults) {
        return jobDAO.getByWorker(id, firstResult, maxResults);
    }

    /**
     * Визначає розробника працівником данної роботи по його та її id.
     * @param jobId id роботи
     * @param devId Id розробника
     */
    public void addDeveloper(long jobId, long devId) {
        DeveloperUser developerUser = developerUserDAO.get(devId);
        Job job = jobDAO.get(jobId);
        job.setDeveloperUser(developerUser);
        jobDAO.update(job);
    }

    /**
     * Видаляє розробника з поста працівника данної роботи по його та її id.
     * @param jobId id роботи
     * @param devId Id розробника
     */
    public void removeDeveloper(long jobId, long devId) {
        DeveloperUser developerUser = developerUserDAO.get(devId);
        developerUser.setRating(developerUser.getRating()-5);
        Job job = jobDAO.get(jobId);
        job.setDeveloperUser(null);
        jobDAO.update(job);
    }

    /**
     * Додає у список претендентів данної роботи розробника по його id.
     * @param jobId id роботи
     * @param devId Id розробника
     */
    public void addApplicant(long jobId, long devId) {
        DeveloperUser developerUser = developerUserDAO.get(devId);
        Job job = jobDAO.get(jobId);
        job.getApplicants().add(developerUser);
        jobDAO.update(job);
    }

    /**
     * Видаляє зі списку претендентів данної роботи розробника по його id.
     * @param jobId id роботи
     * @param devId Id розробника
     */
    public void removeApplicant(long jobId, long devId) {
        DeveloperUser developerUser = developerUserDAO.get(devId);
        Job job = jobDAO.get(jobId);
        job.getApplicants().remove(developerUser);
        jobDAO.update(job);
    }

    /**
     * Перестворюе та оновлюе роботу по новоредагованій роботі, та id редагованої.
     * @param realJobId id роботи, що редагуеться
     * @param tmpJob тимчасова, щойно відредагована робота
     */
    public void realUpdate(Long realJobId, Job tmpJob){
        Job realJob = jobDAO.get(realJobId);
        realJob.setTitle(tmpJob.getTitle());
        realJob.setTags(tmpJob.getTags());
        realJob.setDescription(tmpJob.getDescription());
        realJob.setPrice(tmpJob.getPrice());
        realJob.setAgreement(tmpJob.getAgreement());
        jobDAO.update(realJob);
    }

    /**
     * Змінюе статус роботи по її id.
     * @param id її id
     */
    public void setFinished(Long id) {
        jobDAO.setFinished(id);
    }

    /**
     * Оновлюе роботу по її id.
     * @param object робота, яку треба оновити
     */
    public void update(Job object) {
        jobDAO.update(object);
    }

    /**
     * Видаляе роботу по її id.
     * @param object робота, яку треба видалити
     */
    public void delete(Job object) {
        jobDAO.delete(object);
    }
}
