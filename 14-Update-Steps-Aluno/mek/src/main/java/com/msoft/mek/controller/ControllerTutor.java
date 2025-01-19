package com.msoft.mek.controller;

import com.msoft.mek.dto.tutor.DTOTutor;
import com.msoft.mek.dto.tutor.DTOUpdateEmailTutor;
import com.msoft.mek.dto.tutor.DTOUpdatePasswTutor;
import com.msoft.mek.dto.tutor.DTOUpdateTutor;
import com.msoft.mek.exception.CryptoExistsException;
import com.msoft.mek.exception.EMailExistsException;
import com.msoft.mek.model.ModelArea;
import com.msoft.mek.model.ModelTutor;
import com.msoft.mek.repository.RepositoryArea;
import com.msoft.mek.repository.RepositoryTutor;
import com.msoft.mek.service.ServiceTutor;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

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

        System.out.println("\n/tutor/seveSignin\n");
        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/signinErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            List<String> errors = new ArrayList<>();
            String tutorName = dtoTutor.getTutorName();

            Long areaAptidaoId = dtoTutor.getAreaAptidao();
            ModelArea areaAptidao = repositoryArea.findByAreaId(areaAptidaoId);

            Integer idade = dtoTutor.getIdade();
            Integer anosExperiencia = dtoTutor.getAnosExperiencia();
            String tutorEmail = dtoTutor.getTutorEmail();
            String passw = dtoTutor.getPassw();

            try {
                serviceTutor.validatePasswordSize(passw);
            } catch (IllegalArgumentException e) {
                errors.add(e.getMessage());
            }

            try {
                serviceTutor.validateEmailExistence(tutorEmail);
            } catch (EMailExistsException e) {
                errors.add(e.getMessage());
            }

            try {
                if (errors.isEmpty()) {
                    ModelTutor modelTutor = new ModelTutor(tutorName, areaAptidao, idade, anosExperiencia, tutorEmail, passw);
                    serviceTutor.saveSignin(modelTutor);
                    modelAndView.setViewName("access/login");
                }
            } catch (CryptoExistsException e) {
                errors.add(e.getMessage());
            } catch (Exception e) {
                errors.add("Erro inesperado: " + e.getMessage());
            }

            if (!errors.isEmpty()) {
                modelAndView.setViewName("tutor/signinExceptions");
                modelAndView.addObject("errors", errors);
            }

            return modelAndView;
        }
    } // saveSignin

    @GetMapping("/readOne")
    public ModelAndView readOne (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/tutor/readOne\n");
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
                String modelTutorHttpName = "modelTutor";
                String viewName = "tutor/readOne";

                ModelArea modelArea = modelTutorBanco.getAreaAptidao();
                String modelAreaName = "modelArea";

                modelAndView.addObject(modelAreaName, modelArea);
                modelAndView.addObject(modelTutorHttpName, modelTutorBanco);
                modelAndView.setViewName(viewName);
            }
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // readOne

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

                String modelTutorAreaAptidaoAreaIdName = "modelTutorAreaAptidaoAreaId";
                ModelArea areaAptidao = modelTutorBanco.getAreaAptidao();
                Long modelTutorAreaAptidaoAreaId = areaAptidao.getAreaId();

                modelAndView.addObject(areasName, areas);
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
    public ModelAndView saveUpdate (@Valid DTOUpdateTutor updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/tutor/saveUpdate\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/updateDetailsErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String viewName = "redirect:/tutor/readOne";

            Long updatedTutorId = updating.getTutorId();
            String updatedNome = updating.getTutorName();

            Long updatedAreaAptidaoId = updating.getAreaAptidao();
            ModelArea updatedAreaAptidao = repositoryArea.findByAreaId(updatedAreaAptidaoId);

            Integer updatedIdade = updating.getIdade();
            Integer updatedAnosExperiencia = updating.getAnosExperiencia();

            ModelTutor existing = repositoryTutor.findByTutorId(updatedTutorId);

            existing.setTutorName(updatedNome);
            existing.setAreaAptidao(updatedAreaAptidao);
            existing.setIdade(updatedIdade);
            existing.setAnosExperiencia(updatedAnosExperiencia);

            repositoryTutor.save(existing);

            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    } // saveUpdate

    @GetMapping("/doUpdateEmail")
    public ModelAndView doUpdateEmail (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/tutor/doUpdateEmail\n");
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
                String modelTutorHttpName = "modelTutor";

                String viewName = "tutor/updateEmail";

                modelAndView.addObject(modelTutorHttpName, modelTutorHttp);
                modelAndView.setViewName(viewName);
            }
            else {
                String viewName = "redirect:/aluno/doUpdateEmail";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // doUpdateEmail

    @PostMapping("/saveUpdateEmail")
    public ModelAndView saveUpdateEmail (@Valid DTOUpdateEmailTutor updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/tutor/saveUpdateEmail\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/updateEmailError";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {

            String viewName = "redirect:/tutor/readOne";

            Long updatedTutorId = updating.getTutorId();
            String updatedEmail = updating.getTutorEmail();

            ModelTutor existing = repositoryTutor.findByTutorId(updatedTutorId);

            String error = "Nothing";

            try {
                serviceTutor.validateEmailExistence(updatedEmail);
            } catch (EMailExistsException e) {
                error = e.getMessage();
            }

            if (error == "Nothing") {
                existing.setTutorEmail(updatedEmail);
                repositoryTutor.save(existing);
            }
            else {
                String errorName = "error";
                viewName = "tutor/updateEmailException";
                modelAndView.addObject(errorName, error);
                modelAndView.setViewName(viewName);
            }

            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    } // saveUpdateEmail

    @GetMapping("/doUpdatePassw")
    public ModelAndView doUpdatePassw (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/tutor/doUpdatePassw\n");
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
                String modelTutorHttpName = "modelTutor";
                String viewName = "tutor/updatePassw";

                modelAndView.addObject(modelTutorHttpName, modelTutorHttp);
                modelAndView.setViewName(viewName);
            }
            else {
                String viewName = "redirect:/aluno/doUpdatePassw";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // doUpdatePassw

    @PostMapping("/saveUpdatePassw")
    public ModelAndView saveUpdatePassw (@Valid DTOUpdatePasswTutor updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/tutor/saveUpdatePassw\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/updatePasswError";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {

            List<String> errors = new ArrayList<>();
            Long updatedTutorId = updating.getTutorId();
            String updatedPassw = updating.getPassw();
            ModelTutor existing = repositoryTutor.findByTutorId(updatedTutorId);

            try {
                serviceTutor.validatePasswordSize(updatedPassw);
            } catch (IllegalArgumentException e) {
                errors.add(e.getMessage());
            }

            try {
                if (errors.isEmpty()) {
                    existing.setPassw(updatedPassw);
                    serviceTutor.saveSignin(existing);
                    modelAndView.setViewName("redirect:/tutor/readOne");
                }
            } catch (CryptoExistsException e) {
                errors.add(e.getMessage());
            } catch (Exception e) {
                errors.add("Erro inesperado: " + e.getMessage());
            }

            if (!errors.isEmpty()) {
                modelAndView.setViewName("tutor/updatePasswExceptions");
                modelAndView.addObject("errors", errors);
            }

            return modelAndView;
        }
    } // saveUpdatePassw

} // ControllerTutor