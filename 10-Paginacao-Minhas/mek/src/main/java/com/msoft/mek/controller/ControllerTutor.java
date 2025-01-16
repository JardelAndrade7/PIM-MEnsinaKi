package com.msoft.mek.controller;

import com.msoft.mek.general.DTOTutor;
import com.msoft.mek.model.ModelArea;
import com.msoft.mek.model.ModelTutor;
import com.msoft.mek.repository.RepositoryArea;
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
    private RepositoryArea repositoryArea;

    @GetMapping("/doSignin")
    public ModelAndView doSignin (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/tutor/doSignin\n");
        String userLogadoName = "usuarioLogado";
        Object userLogado = http.getAttribute(userLogadoName);
        if(userLogado == null) {
            String areasName = "areas";
            Iterable<ModelArea> areas = repositoryArea.findAll();
            String viewName = "tutor/signin";
            modelAndView.addObject(areasName, areas);
            modelAndView.setViewName(viewName);
        }
        else {
            String viewName = "redirect:/";
            modelAndView.setViewName(viewName);
        }
        return modelAndView;
    }

    @PostMapping("/saveSignin")
    public ModelAndView saveSignin(@Valid DTOTutor dtoTutor, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/tutor/saveSignin\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/signinErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String viewName = "access/login";

            String tutorName = dtoTutor.getTutorName();

            Long areaFormacaoId = dtoTutor.getAreaFormacao();
            ModelArea areaFormacao = repositoryArea.findByAreaId(areaFormacaoId);

            Long areaAptidaoId = dtoTutor.getAreaAptidao();
            ModelArea areaAptidao = repositoryArea.findByAreaId(areaAptidaoId);

            Integer idade = dtoTutor.getIdade();
            Integer anosExperiencia = dtoTutor.getAnosExperiencia();
            String tutorEmail = dtoTutor.getTutorEmail();
            String passw = dtoTutor.getPassw();

            ModelTutor modelTutor = new ModelTutor(tutorName, areaFormacao, areaAptidao, idade, anosExperiencia, tutorEmail, passw);

            modelAndView.setViewName(viewName);
            serviceTutor.saveSignin(modelTutor);
            return modelAndView;
        }
    }

    @GetMapping("/doUpdate")
    public ModelAndView doUpdate (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/tutor/doUpdate\n");
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
                Long httpCodigo = modelTutorHttp.getTutorId();
                ModelTutor modelTutorBanco = repositoryTutor.findByTutorId(httpCodigo);
                String modelAlunoName = "modelTutor";
                String viewName = "tutor/update";

                String areasName = "areas";
                Iterable<ModelArea> areas = repositoryArea.findAll();

                String modelTutorAreaFormacaoAreaIdName = "modelTutorAreaFormacaoAreaId";
                ModelArea areaFormacao = modelTutorBanco.getAreaFormacao();
                Long modelTutorAreaFormacaoAreaId = areaFormacao.getAreaId();

                String modelTutorAreaAptidaoAreaIdName = "modelTutorAreaAptidaoAreaId";
                ModelArea areaAptidao = modelTutorBanco.getAreaAptidao();
                Long modelTutorAreaAptidaoAreaId = areaAptidao.getAreaId();

                modelAndView.addObject(areasName, areas);
                modelAndView.addObject(modelTutorAreaFormacaoAreaIdName, modelTutorAreaFormacaoAreaId);
                modelAndView.addObject(modelTutorAreaAptidaoAreaIdName, modelTutorAreaAptidaoAreaId);
                modelAndView.addObject(modelAlunoName, modelTutorBanco);
                modelAndView.setViewName(viewName);
            }
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // doUpdate

    @PostMapping("/saveUpdate")
    public ModelAndView saveUpdate (@Valid DTOTutor updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/tutor/saveUpdate\n");

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

            Long updatedAreaFormacaoId = updating.getAreaFormacao();
            ModelArea updatedAreaFormacao = repositoryArea.findByAreaId(updatedAreaFormacaoId);
            Long updatedAreaAptidaoId = updating.getAreaAptidao();
            ModelArea updatedAreaAptidao = repositoryArea.findByAreaId(updatedAreaAptidaoId);

            Integer updatedIdade = updating.getIdade();
            Integer updatedAnosExperiencia = updating.getAnosExperiencia();
            String updatedEmail = updating.getTutorEmail();
            String updatedPass = updating.getPassw();

            ModelTutor existing = repositoryTutor.findByEMail(updatedEmail);

            existing.setTutorName(updatedNome);
            existing.setAreaFormacao(updatedAreaFormacao);
            existing.setAreaAptidao(updatedAreaAptidao);
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