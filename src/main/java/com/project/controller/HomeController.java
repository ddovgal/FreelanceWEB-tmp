package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public ModelAndView onIndex() {
        ModelAndView modelAndView = new ModelAndView("public/index");
        return modelAndView;
    }

//    @RequestMapping("/view/res/style.css")
//    public ModelAndView getStyle() {
//        ModelAndView modelAndView = new ModelAndView("public/res");
//        return modelAndView;
//    }

    
}
