package com.msoft.mek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/access")
public class AccessController {
    @GetMapping("/tryLogin")
    public ModelAndView tryLogin () {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "access/login";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @GetMapping("/choiceSigninUser")
    public ModelAndView choiceSigninUser () {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "access/choiceSigninUser";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
