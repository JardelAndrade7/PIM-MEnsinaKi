package com.msoft.mek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/public")
public class ControllerPublic {

    @GetMapping("/howToUse")
    public ModelAndView howToUse (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/public/howToUse\n");
        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(usuarioName);
        if(usuarioLogadoObject == null) {
            String viewName = "public/naoLogado/howToUse";
            modelAndView.setViewName(viewName);
        }
        else {
            Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
            String usuarioLogadoName = usuarioLogadoClass.getName();
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            if (usuarioLogadoNameTrue) {
                String viewName = "public/aluno/howToUse";
                modelAndView.setViewName(viewName);
            }
            else {
                String viewName = "public/tutor/howToUse";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // howToUse

    @GetMapping("/whatIs")
    public ModelAndView whatIs (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/public/howToUse\n");
        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(usuarioName);
        if(usuarioLogadoObject == null) {
            String viewName = "public/naoLogado/whatIs";
            modelAndView.setViewName(viewName);
        }
        else {
            Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
            String usuarioLogadoName = usuarioLogadoClass.getName();
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            if (usuarioLogadoNameTrue) {
                String viewName = "public/aluno/whatIs";
                modelAndView.setViewName(viewName);
            }
            else {
                String viewName = "public/tutor/whatIs";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // whatIs

}