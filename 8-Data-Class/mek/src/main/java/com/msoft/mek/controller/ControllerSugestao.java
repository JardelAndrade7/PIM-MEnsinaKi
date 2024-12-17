package com.msoft.mek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sugestao")
public class ControllerSugestao {
    @GetMapping("/tryCreate")
    public ModelAndView tryCreate () {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "sugestao/create";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @GetMapping("/seeTutorList")
    public ModelAndView seeTutorList () {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "sugestao/tutorList";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @GetMapping("/tryUpdate")
    public ModelAndView tryUpdate () {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "sugestao/update";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}