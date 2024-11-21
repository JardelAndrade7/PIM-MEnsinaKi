package com.msoft.mek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewsController {
    @GetMapping("/")
    public ModelAndView seeViewList () {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "access/viewsList";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
