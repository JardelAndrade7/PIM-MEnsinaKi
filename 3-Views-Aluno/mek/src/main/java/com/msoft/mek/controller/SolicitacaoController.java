package com.msoft.mek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/solicitacao")
public class SolicitacaoController {
    @GetMapping("/tryCreate")
    public ModelAndView tryCreate () {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "solicitacao/create";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @GetMapping("/seeAlunoList")
    public ModelAndView seeAlunoList () {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "solicitacao/alunoList";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @GetMapping("/tryUpdate")
    public ModelAndView tryUpdate () {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = "solicitacao/update";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
