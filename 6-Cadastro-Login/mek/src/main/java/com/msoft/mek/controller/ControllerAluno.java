package com.msoft.mek.controller;

import com.msoft.mek.model.ModelAluno;
import com.msoft.mek.repository.RepositoryAluno;
import com.msoft.mek.service.ServiceAluno;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/aluno")
public class ControllerAluno {
    @Autowired
    private RepositoryAluno repositoryAluno;
    @Autowired
    private ServiceAluno serviceAluno;
    @Autowired
    private ControllerAccess controllerAccess;

    @GetMapping("/doSignin")
    public ModelAndView doSignin () {
        ModelAndView modelAndView = new ModelAndView();
        ModelAluno modelAluno = new ModelAluno();
        String viewName = "aluno/signin";
        String modelAlunoName = "modelAluno";
        modelAndView.setViewName(viewName);
        modelAndView.addObject(modelAlunoName, modelAluno);
        return modelAndView;
    }

    @PostMapping("/saveSignin")
    public ModelAndView saveSignin(@Valid ModelAluno modelAluno, BindingResult binding) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/signinErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            serviceAluno.saveSignin(modelAluno);
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
        String comMmSoft = "com.msoft.mek.model.ModelAluno";
        boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
        if (usuarioLogadoNameTrue) {
            ModelAluno modelAlunoHttp = (ModelAluno) usuarioLogadoObject;
            long httpCodigo = modelAlunoHttp.getAlunoId();
            ModelAluno modelAlunoBanco = repositoryAluno.findByAlunoId(httpCodigo);
            String modelAlunoName = "modelAluno";
            String viewName = "aluno/update";
            modelAndView.addObject(modelAlunoName, modelAlunoBanco);
            modelAndView.setViewName(viewName);
        }
        else {
        }
        return modelAndView;
    } // doUpdate

    @PostMapping("/saveUpdate")
    public ModelAndView saveUpdate (@Valid ModelAluno updating, BindingResult binding) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/atualizacaoSigninErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String viewName = "redirect:/";

            String updatedNome = updating.getAlunoName();
            String updatedSerie = updating.getSerie();
            Integer updatedIdade = updating.getIdade();
            String updatedEmail = updating.getAlunoEmail();
            String updatedPass = updating.getPassw();

            ModelAluno existing = repositoryAluno.findByEMail(updatedEmail);

            existing.setAlunoName(updatedNome);
            existing.setSerie(updatedSerie);
            existing.setIdade(updatedIdade);
            existing.setAlunoEmail(updatedEmail);
            existing.setPassw(updatedPass);

            serviceAluno.updateProfile(existing);

            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    } // saveUpdate

} // ControllerAluno