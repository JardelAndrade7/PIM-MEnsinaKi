package com.msoft.mek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/aluno")
public class AlunoController {
    @GetMapping("/doSignin")
    public ModelAndView doSignin () {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "aluno/signin";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @GetMapping("/seeFeed")
    public ModelAndView seeFeed () {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "aluno/feed";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @GetMapping("/tryUpdate")
    public ModelAndView tryUpdate () {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "aluno/update";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
