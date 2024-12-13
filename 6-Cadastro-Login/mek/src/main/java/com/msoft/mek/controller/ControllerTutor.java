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
    @Autowired
    private ControllerAccess controllerAccess;

    @GetMapping("/doSignin")
    public ModelAndView doSignin () {
        ModelAndView modelAndView = new ModelAndView();
        ModelTutor modelTutor = new ModelTutor();
        String viewName = "tutor/signin";
        String modelTutorName = "modelTutor";
        modelAndView.setViewName(viewName);
        modelAndView.addObject(modelTutorName, modelTutor);
        return modelAndView;
    }

    @PostMapping("/saveSignin")
    public ModelAndView saveSignin(@Valid ModelTutor modelTutor, BindingResult binding) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/signinErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            serviceTutor.saveSignin(modelTutor);
            return controllerAccess.tryLogin();
        }
    }

    @GetMapping("/doUpdate")
    public ModelAndView doUpdate (HttpSession http) {
        ModelAndView modelAndView = new ModelAndView();
        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(usuarioName);
        Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
        String usuarioLogadoName = usuarioLogadoClass.getName();
        String comMmSoft = "com.msoft.mek.model.ModelTutor";
        boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
        if (usuarioLogadoNameTrue) {
            ModelTutor modelTutorHttp = (ModelTutor) usuarioLogadoObject;
            long httpCodigo = modelTutorHttp.getTutorId();
            ModelTutor modelTutorBanco = repositoryTutor.findByTutorId(httpCodigo);
            String modelTutorName = "modelTutor";
            String viewName = "tutor/update";
            modelAndView.addObject(modelTutorName, modelTutorBanco);
            modelAndView.setViewName(viewName);
        }
        else {
        }
        return modelAndView;
    } // doUpdate

    @PostMapping("/saveUpdate")
    public ModelAndView saveUpdate (@Valid ModelTutor updating, BindingResult binding) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

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