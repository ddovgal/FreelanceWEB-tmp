package com.project.controller;

import com.project.businesslogic.Image;
import com.project.dao.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Collection;

/**
 * Клас - контролер. Саме в цьому класі виконуеться обробка запитів, та видача відповідей на запит. Данний контролер
 * оброблюе глобальні запити, потрібні для усіх типів користувачів, а також незайшовших у систему гостей.
 */
@Controller
public class BasicController {

    private ImageDAO imageDAO;

    @Autowired
    public void setImageDAO(ImageDAO imageDAO) {
        this.imageDAO = imageDAO;
    }

    /**
     * Повернення на початкову сторінку.
     * @return модель-представлення сторінки
     */
    @RequestMapping("/home")
    public ModelAndView onIndex() {
        ModelAndView modelAndView = new ModelAndView("public/index");
        return modelAndView;
    }

    /**
     * Редірект до профілю користувача в залежності від його типу.
     * @return модель-представлення сторінки
     */
    @RequestMapping("/p-redirect")
    public ModelAndView profileRedirect() {
        ModelAndView modelAndView = null;
        if (hasRole("ROLE_DEVELOPER")) {
            System.out.println("Has role ROLE_DEVELOPER");
            modelAndView = new ModelAndView("redirect:/usr/developer/profile");
        }
        if (hasRole("ROLE_CUSTOMER")) {
            System.out.println("Has role ROLE_CUSTOMER");
            modelAndView = new ModelAndView("redirect:/usr/customer/profile");
        }
        if (hasRole("ROLE_ADMIN")) {
            System.out.println("Has role ROLE_ADMIN");
            modelAndView = new ModelAndView("private/admin/profile");
        }
        return modelAndView;
    }

    /**
     * Перевірка на певну роль.
     * @param role роль
     * @return чи цього користувач типу
     */
    private boolean hasRole(String role) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals(role);
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }

    /**
     * Для обробки запросу на отримання аватару користувача по його id.
     * @param userId його id
     * @return його аватар
     */
    @RequestMapping(value = "/user/image/{userId}")
    public ResponseEntity<byte[]> getCustomerImage(@PathVariable("userId") Long userId) throws IOException {
        final HttpHeaders headers = new HttpHeaders();

        //just for "about us" page
        if (userId==-1) return new ResponseEntity<byte[]>(imageDAO.getByName("dmitriy").getImage(), headers, HttpStatus.OK);
        /*if (userId==-2) return new ResponseEntity<byte[]>(imageDAO.getByName("grigoriy").getImage(), headers, HttpStatus.OK);*/ //TODO: change
        if (userId==-2) return new ResponseEntity<byte[]>(imageDAO.getByName("anonymous").getImage(), headers, HttpStatus.OK);
        if (userId==-3) return new ResponseEntity<byte[]>(imageDAO.getByName("eugen").getImage(), headers, HttpStatus.OK);

        Image image = imageDAO.getByUserId(userId);
        if (image == null) image = imageDAO.getByName("default_avatar");

        return new ResponseEntity<byte[]>(image.getImage(), headers, HttpStatus.OK);
    }

}
