package com.msoft.mek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tutor")
public class TutorController {
    @GetMapping("/doSignin")
    public ModelAndView doSignin () {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "tutor/signin";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
