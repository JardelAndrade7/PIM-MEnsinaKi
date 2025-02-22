package com.msoft.mek.controller;

import com.msoft.mek.dto.tutor.DTOTutorEmail;
import com.msoft.mek.dto.tutor.DTOTutorPassword;
import com.msoft.mek.dto.tutor.DTOTutorDetails;
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

    @GetMapping("/doSigninDetails")
    public ModelAndView doSignin (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/tutor/doSigninDetails\n");
        String userLogadoName = "usuarioLogado";
        Object userLogado = http.getAttribute(userLogadoName);
        if(userLogado == null) {
            String areasName = "areas";
            Iterable<ModelArea> areas = repositoryArea.findAll();
            String viewName = "tutor/signin/signinDetails";
            modelAndView.addObject(areasName, areas);
            modelAndView.setViewName(viewName);
        }
        else {
            String viewName = "redirect:/";
            modelAndView.setViewName(viewName);
        }
        return modelAndView;
    } // doSigninDetails

    @PostMapping("/saveSigninDetails")
    public ModelAndView saveSigninDetails(@Valid DTOTutorDetails dtoTutorDetails, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/tutor/saveSigninDetails\n");
        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/signin/signinDetailsErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String dtoTutorDetailsName = "dtoTutorDetails";
            String viewName = "tutor/signin/signinEmail";
            modelAndView.addObject(dtoTutorDetailsName, dtoTutorDetails);
            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    } // saveSigninDetails

    @PostMapping("/saveSigninEmail")
    public ModelAndView saveSigninEmail(@Valid DTOTutorEmail dtoTutorEmail, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/tutor/saveSigninEmail\n");
        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/signin/signinEmailError";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String tutorEmail = dtoTutorEmail.getTutorEmail();
            String error = "Nothing";

            try {
                serviceTutor.validateEmailExistence(tutorEmail);
            } catch (EMailExistsException e) {
                error = e.getMessage();
            }

            if (error == "Nothing") {
                String viewName = "tutor/signin/signinPassword";
                String dtoTutorEmailName = "dtoTutorEmail";
                modelAndView.addObject(dtoTutorEmailName, dtoTutorEmail);
                modelAndView.setViewName(viewName);
            }
            else {
                String errorName = "error";
                String viewName = "tutor/signin/signinEmailException";
                modelAndView.addObject(errorName, error);
                modelAndView.setViewName(viewName);
            }

            return modelAndView;
        }
    } // saveSigninEmail

    @PostMapping("/saveSigninPassword")
    public ModelAndView saveSigninPassword(@Valid DTOTutorPassword dtoTutorPassword, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/tutor/saveSigninPassword\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/signin/signinPasswordError";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            List<String> errors = new ArrayList<>();
            String passw = dtoTutorPassword.getPassw();

            try {
                serviceTutor.validatePasswordSize(passw);
            } catch (IllegalArgumentException e) {
                errors.add(e.getMessage());
            }

            try {
                if (errors.isEmpty()) {
                    String tutorName = dtoTutorPassword.getTutorName();

                    Long areaId = dtoTutorPassword.getAreaAptidao();
                    ModelArea areaAptidao = repositoryArea.findByAreaId(areaId);
        
                    Integer idade = dtoTutorPassword.getIdade();
                    Integer anosExperiencia = dtoTutorPassword.getAnosExperiencia();
                    String tutorEmail = dtoTutorPassword.getTutorEmail();

                    ModelTutor modelTutor = new ModelTutor(tutorName, areaAptidao, idade, anosExperiencia, tutorEmail, passw);

                    String viewName = "access/login";
                    serviceTutor.saveSignin(modelTutor);
                    modelAndView.setViewName(viewName);
                }
            } catch (CryptoExistsException e) {
                errors.add(e.getMessage());
            } catch (Exception e) {
                errors.add("Erro inesperado: " + e.getMessage());
            }

            if (!errors.isEmpty()) {
                modelAndView.setViewName("tutor/signin/signinPasswordExceptions");
                modelAndView.addObject("errors", errors);
            }

            return modelAndView;
        }
    } // saveSigninPassword

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

    @GetMapping("/doUpdateDetails")
    public ModelAndView doUpdateDetails (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/tutor/doUpdateDetails\n");
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
                String viewName = "tutor/update/updateDetails";

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
    } // doUpdateDetails

    @PostMapping("/saveUpdateDetails")
    public ModelAndView saveUpdate (@Valid DTOTutorDetails updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/tutor/saveUpdateDetails\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/update/updateDetailsErrors";
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
    } // saveUpdateDetails

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
                String dtoTutorDetailsName = "dtoTutorDetails";
                String viewName = "tutor/update/updateEmail";

                Long tutorId = modelTutorHttp.getTutorId();
                String tutorName = modelTutorHttp.getTutorName();
                ModelArea modelAreaAptidao = modelTutorHttp.getAreaAptidao();
                Long areaAptidao = modelAreaAptidao.getAreaId();
                Integer idade = modelTutorHttp.getIdade();
                Integer anosExperiencia = modelTutorHttp.getAnosExperiencia();

                DTOTutorDetails dtoTutorDetails = new DTOTutorDetails(tutorId, tutorName, areaAptidao, idade, anosExperiencia);

                modelAndView.addObject(dtoTutorDetailsName, dtoTutorDetails);
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
    public ModelAndView saveUpdateEmail (@Valid DTOTutorEmail updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/tutor/saveUpdateEmail\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/update/updateEmailError";
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
                viewName = "tutor/update/updateEmailException";
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
                String dtoTutorEmailName = "dtoTutorEmail";
                String viewName = "tutor/update/updatePassw";

                Long tutorId = modelTutorHttp.getTutorId();
                String tutorName = modelTutorHttp.getTutorName();
                ModelArea modelAreaAptidao = modelTutorHttp.getAreaAptidao();
                Long areaAptidao = modelAreaAptidao.getAreaId();
                Integer idade = modelTutorHttp.getIdade();
                Integer anosExperiencia = modelTutorHttp.getAnosExperiencia();
                String tutorEmail = modelTutorHttp.getTutorEmail();

                DTOTutorEmail dtoTutorEmail = new DTOTutorEmail(tutorId, tutorName, areaAptidao, idade, anosExperiencia, tutorEmail);

                modelAndView.addObject(dtoTutorEmailName, dtoTutorEmail);
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
    public ModelAndView saveUpdatePassw (@Valid DTOTutorPassword updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/tutor/saveUpdatePassw\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "tutor/update/updatePasswError";
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
                modelAndView.setViewName("tutor/update/updatePasswExceptions");
                modelAndView.addObject("errors", errors);
            }

            return modelAndView;
        }
    } // saveUpdatePassw

} // ControllerTutor