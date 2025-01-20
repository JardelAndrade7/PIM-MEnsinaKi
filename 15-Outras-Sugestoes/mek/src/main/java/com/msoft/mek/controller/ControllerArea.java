package com.msoft.mek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.msoft.mek.model.ModelArea;
import com.msoft.mek.repository.RepositoryArea;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/area")
public class ControllerArea {

    @Autowired
    RepositoryArea repositoryArea;

    @GetMapping("/readAll")
    public ModelAndView readAll (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/area/readAll\n");
        String userLogadoName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(userLogadoName);
        // Se o usuário não estiver logado
        if(usuarioLogadoObject == null) {
            String loginView = "access/login";
            modelAndView.setViewName(loginView);
        }
        // Se o usuário estiver logado
        else {
            Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
            String usuarioLogadoClassName = usuarioLogadoClass.getName();
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoClassName.equals(comMmSoft);
            // Se for Aluno
            if (usuarioLogadoNameTrue) {
                Iterable<ModelArea> areas = repositoryArea.findAll();
                String areasName = "areas";
                modelAndView.addObject(areasName, areas);
                String viewName = "area/readAllToAluno";
                modelAndView.setViewName(viewName);
            }
            // Se for Tutor
            else {
                Iterable<ModelArea> areas = repositoryArea.findAll();
                String areasName = "areas";
                modelAndView.addObject(areasName, areas);
                String viewName = "area/readAllToTutor";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // readAll
}
