package com.msoft.mek.controller;

import com.msoft.mek.model.ModelTutor;
import com.msoft.mek.repository.RepositoryTutor;
import com.msoft.mek.service.ServiceTutor;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tutor")
public class ControllerTutor {

    @Autowired
    private RepositoryTutor repositoryTutor;
    @Autowired
    private ServiceTutor serviceTutor;

    @GetMapping("/doSignin")
    public ModelAndView doSignin (HttpSession http, ModelAndView modelAndView) {
        String userLogadoName = "usuarioLogado";
        Object userLogado = http.getAttribute(userLogadoName);
        if(userLogado == null) {
            String loginView = "tutor/signin";
            modelAndView.setViewName(loginView);
        }
        else {
            String viewName = "redirect:/";
            modelAndView.setViewName(viewName);
        }
        return modelAndView;
    }

    @PostMapping("/saveSignin")
    public ModelAndView saveSignin(@Valid ModelTutor modelTutor, BindingResult binding, ModelAndView modelAndView) throws Exception {

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/signinErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String viewName = "access/login";
            modelAndView.setViewName(viewName);
            serviceTutor.saveSignin(modelTutor);
            return modelAndView;
        }
    }

    @GetMapping("/doUpdate")
    public ModelAndView doUpdate (HttpSession http, ModelAndView modelAndView) {
        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(usuarioName);
        if(usuarioLogadoObject == null) {
            String loginView = "access/login";
            modelAndView.setViewName(loginView);
        }
        else {
            Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
            String usuarioLogadoName = usuarioLogadoClass.getName();
            String comMmSoft = "com.msoft.mek.model.ModelTutor";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            if (usuarioLogadoNameTrue) {
                ModelTutor modelTutorHttp = (ModelTutor) usuarioLogadoObject;
                long httpCodigo = modelTutorHttp.getTutorId();
                ModelTutor modelTutorBanco = repositoryTutor.findByTutorId(httpCodigo);
                String modelAlunoName = "modelTutor";
                String viewName = "tutor/update";
                modelAndView.addObject(modelAlunoName, modelTutorBanco);
                modelAndView.setViewName(viewName);
            }
            else {
            }
        }
        return modelAndView;
    } // doUpdate

    @PostMapping("/saveUpdate")
    public ModelAndView saveUpdate (@Valid ModelTutor updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/atualizacaoSigninErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String viewName = "redirect:/";

            String updatedNome = updating.getTutorName();
            String updatedFormacao = updating.getFormacao();
            Integer updatedIdade = updating.getIdade();
            Integer updatedAnosExperiencia = updating.getAnosExperiencia();
            String updatedEmail = updating.getTutorEmail();
            String updatedPass = updating.getPassw();

            ModelTutor existing = repositoryTutor.findByEMail(updatedEmail);

            existing.setTutorName(updatedNome);
            existing.setFormacao(updatedFormacao);
            existing.setIdade(updatedIdade);
            existing.setAnosExperiencia(updatedAnosExperiencia);
            existing.setTutorEmail(updatedEmail);
            existing.setPassw(updatedPass);

            serviceTutor.updateProfile(existing);

            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    } // saveUpdate

} // ControllerTutor