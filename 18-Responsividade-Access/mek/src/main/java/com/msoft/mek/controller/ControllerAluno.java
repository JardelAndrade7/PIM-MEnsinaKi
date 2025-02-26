package com.msoft.mek.controller;

import com.msoft.mek.dto.aluno.DTOAlunoArea;
import com.msoft.mek.dto.aluno.DTOAlunoDetails;
import com.msoft.mek.dto.aluno.DTOAlunoEmail;
import com.msoft.mek.dto.aluno.DTOAlunoPassword;
import com.msoft.mek.dto.aluno.DTOAlunoSerie;
import com.msoft.mek.exception.CryptoExistsException;
import com.msoft.mek.exception.EMailExistsException;
import com.msoft.mek.model.ModelAluno;
import com.msoft.mek.model.ModelArea;
import com.msoft.mek.model.ModelSerie;
import com.msoft.mek.repository.RepositoryAluno;
import com.msoft.mek.repository.RepositoryArea;
import com.msoft.mek.repository.RepositorySerie;
import com.msoft.mek.service.ServiceAluno;
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
@RequestMapping("/aluno")
public class ControllerAluno {

    @Autowired
    private RepositoryAluno repositoryAluno;

    @Autowired
    private ServiceAluno serviceAluno;

    @Autowired
    private RepositoryArea repositoryArea;

    @Autowired
    private RepositorySerie repositorySerie;

    @GetMapping("/doSigninDetails")
    public ModelAndView doSigninDetails (HttpSession http, ModelAndView modelAndView) {

        System.out.println("\n/aluno/doSigninDetails\n");
        String userLogadoName = "usuarioLogado";
        Object userLogado = http.getAttribute(userLogadoName);
        if(userLogado == null) {
            String viewName = "aluno/signin/signinDetails";
            modelAndView.setViewName(viewName);
        }
        else {
            String viewName = "redirect:/";
            modelAndView.setViewName(viewName);
        }
        return modelAndView;
    } // doSigninDetails

    @PostMapping("/saveSigninDetails")
    public ModelAndView saveSigninDetails(@Valid DTOAlunoDetails dtoAlunoDetails, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/saveSigninDetails\n");
        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/signin/signinDetailsErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {

            String seriesName = "series";
            Iterable<ModelSerie> series = repositorySerie.findAll();

            String viewName = "aluno/signin/signinSerie";

            String dtoAlunoDetailsName = "dtoAlunoDetails";

            modelAndView.addObject(seriesName, series);
            modelAndView.addObject(dtoAlunoDetailsName, dtoAlunoDetails);
            
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
    } // saveSigninDetails

    @PostMapping("/saveSigninSerie")
    public ModelAndView saveSigninSerie(@Valid DTOAlunoSerie dtoAlunoSerie, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/saveSigninSerie\n");
        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/signin/signinDetailsErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            
            String areasName = "areas";
            Iterable<ModelArea> areas = repositoryArea.findAll();

            modelAndView.addObject(areasName, areas);
            
            String viewName = "aluno/signin/signinArea";

            String dtoAlunoSerieName = "dtoAlunoSerie";

            modelAndView.addObject(dtoAlunoSerieName, dtoAlunoSerie);

            modelAndView.setViewName(viewName);
            return modelAndView;
        }
    } // saveSigninSerie

    @PostMapping("/saveSigninArea")
    public ModelAndView saveSigninArea(@Valid DTOAlunoArea dtoAlunoArea, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/saveSigninArea\n");
        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/signin/signinDetailsErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            
            String viewName = "aluno/signin/signinEmail";

            String dtoAlunoAreaName = "dtoAlunoArea";

            modelAndView.addObject(dtoAlunoAreaName, dtoAlunoArea);
            
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
    } // saveSigninArea

    @PostMapping("/saveSigninEmail")
    public ModelAndView saveSigninEmail(@Valid DTOAlunoEmail dtoAlunoEmail, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/saveSigninEmail\n");
        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/signin/signinEmailError";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String error = "Nothing";
            String alunoEmail = dtoAlunoEmail.getAlunoEmail();

            try {
                serviceAluno.validateEmailExistence(alunoEmail);
            } catch (EMailExistsException e) {
                error = e.getMessage();
            }

            if (error.equals("Nothing")) {
                String viewName = "aluno/signin/signinPassword";
                String dtoAlunoEmailName = "dtoAlunoEmail";
                modelAndView.addObject(dtoAlunoEmailName, dtoAlunoEmail);
                modelAndView.setViewName(viewName);
            }
            else {
                String errorName = "error";
                String viewName = "aluno/signin/signinEmailException";
                modelAndView.addObject(errorName, error);
                modelAndView.setViewName(viewName);
            }

            return modelAndView;
        }
    } // saveSigninEmail

    @PostMapping("/saveSigninPassword")
    public ModelAndView saveSigninPassword(@Valid DTOAlunoPassword dtoAlunoPassword, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/saveSigninPassword\n");
        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/signin/signinPasswordError";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            List<String> errors = new ArrayList<>();
            String passw = dtoAlunoPassword.getPassw();

            try {
                serviceAluno.validatePasswordSize(passw);
            } catch (IllegalArgumentException e) {
                errors.add(e.getMessage());
            }

            try {
                if (errors.isEmpty()) {
                    String alunoName = dtoAlunoPassword.getAlunoName();

                    Long serieId = dtoAlunoPassword.getSerie();
                    ModelSerie serie = repositorySerie.findBySerieId(serieId);
        
                    Long areaId = dtoAlunoPassword.getAreaDificuldade();
                    ModelArea areaDificuldade = repositoryArea.findByAreaId(areaId);
        
                    Integer idade = dtoAlunoPassword.getIdade();
                    String alunoEmail = dtoAlunoPassword.getAlunoEmail();

                    ModelAluno modelAluno = new ModelAluno(alunoName, serie, areaDificuldade, idade, alunoEmail, passw);
                    
                    serviceAluno.saveSignin(modelAluno);
                    modelAndView.setViewName("access/login");
                }
            } catch (CryptoExistsException e) {
                errors.add(e.getMessage());
            } catch (Exception e) {
                errors.add("Erro inesperado: " + e.getMessage());
            }

            if (!errors.isEmpty()) {
                modelAndView.setViewName("aluno/signin/signinPasswordExceptions");
                modelAndView.addObject("errors", errors);
            }

            return modelAndView;
        }
    } // saveSigninPassword

    @GetMapping("/readOne")
    public ModelAndView readOne (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/aluno/readOne\n");
        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(usuarioName);
        if(usuarioLogadoObject == null) {
            String loginView = "access/login";
            modelAndView.setViewName(loginView);
        }
        else {
            Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
            String usuarioLogadoName = usuarioLogadoClass.getName();
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            if (usuarioLogadoNameTrue) {
                ModelAluno modelAlunoHttp = (ModelAluno) usuarioLogadoObject;
                Long httpCodigo = modelAlunoHttp.getAlunoId();
                ModelAluno modelAlunoBanco = repositoryAluno.findByAlunoId(httpCodigo);
                String modelAlunoName = "modelAluno";
                String viewName = "aluno/readOne";

                ModelArea modelArea = modelAlunoBanco.getAreaDificuldade();
                String modelAreaName = "modelArea";

                ModelSerie modelSerie = modelAlunoBanco.getSerie();
                String modelSerieName = "modelSerie";

                modelAndView.addObject(modelSerieName, modelSerie);
                modelAndView.addObject(modelAreaName, modelArea);
                modelAndView.addObject(modelAlunoName, modelAlunoBanco);
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
        System.out.println("\n/aluno/doUpdateDetails\n");
        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(usuarioName);
        if(usuarioLogadoObject == null) {
            String loginView = "access/login";
            modelAndView.setViewName(loginView);
        }
        else {
            Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
            String usuarioLogadoName = usuarioLogadoClass.getName();
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            if (usuarioLogadoNameTrue) {
                ModelAluno modelAlunoHttp = (ModelAluno) usuarioLogadoObject;
                Long httpCodigo = modelAlunoHttp.getAlunoId();
                ModelAluno modelAlunoBanco = repositoryAluno.findByAlunoId(httpCodigo);
                String modelAlunoName = "modelAluno";

                String viewName = "aluno/update/updateDetails";

                modelAndView.addObject(modelAlunoName, modelAlunoBanco);
                modelAndView.setViewName(viewName);
            }
            else {
                String viewName = "redirect:/tutor/doUpdate";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // doUpdateDetails

    @PostMapping("/saveUpdateDetails")
    public ModelAndView saveUpdateDetails (@Valid DTOAlunoDetails updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/saveUpdateDetails\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/update/updateDetailsErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String viewName = "redirect:/aluno/readOne";

            Long updatedAlunoId = updating.getAlunoId();
            ModelAluno existing = repositoryAluno.findByAlunoId(updatedAlunoId);

            String updatedNome = updating.getAlunoName();
            Integer updatedIdade = updating.getIdade();

            existing.setAlunoName(updatedNome);
            existing.setIdade(updatedIdade);

            repositoryAluno.save(existing);

            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    } // saveUpdateDetails

    @GetMapping("/doUpdateSerie")
    public ModelAndView doUpdateSerie (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/aluno/doUpdateSerie\n");
        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(usuarioName);
        if(usuarioLogadoObject == null) {
            String loginView = "access/login";
            modelAndView.setViewName(loginView);
        }
        else {
            Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
            String usuarioLogadoName = usuarioLogadoClass.getName();
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            if (usuarioLogadoNameTrue) {
                ModelAluno modelAlunoHttp = (ModelAluno) usuarioLogadoObject;
                Long httpCodigo = modelAlunoHttp.getAlunoId();
                ModelAluno modelAlunoBanco = repositoryAluno.findByAlunoId(httpCodigo);
                String modelAlunoName = "modelAluno";

                String seriesName = "series";
                Iterable<ModelSerie> series = repositorySerie.findAll();

                ModelSerie modelSerie = modelAlunoBanco.getSerie();
                Long modelAlunoSerieSerieId = modelSerie.getSerieId();
                String modelAlunoSerieSerieIdName = "modelAlunoSerieSerieId";

                String viewName = "aluno/update/updateSerie";

                modelAndView.addObject(modelAlunoName, modelAlunoBanco);
                modelAndView.addObject(seriesName, series);
                modelAndView.addObject(modelAlunoSerieSerieIdName, modelAlunoSerieSerieId);
                modelAndView.setViewName(viewName);
            }
            else {
                String viewName = "redirect:/tutor/doUpdate";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // doUpdateSerie

    @PostMapping("/saveUpdateSerie")
    public ModelAndView saveUpdateSerie (@Valid DTOAlunoSerie updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/saveUpdateSerie\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/update/updateDetailsErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String viewName = "redirect:/aluno/readOne";

            Long updatedAlunoId = updating.getAlunoId();
            ModelAluno existing = repositoryAluno.findByAlunoId(updatedAlunoId);

            Long updatedSerieId = updating.getSerie();
            ModelSerie updatedSerie = repositorySerie.findBySerieId(updatedSerieId);

            existing.setSerie(updatedSerie);

            repositoryAluno.save(existing);

            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    } // saveUpdateSerie

    @GetMapping("/doUpdateArea")
    public ModelAndView doUpdateArea (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/aluno/doUpdateArea\n");
        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(usuarioName);
        if(usuarioLogadoObject == null) {
            String loginView = "access/login";
            modelAndView.setViewName(loginView);
        }
        else {
            Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
            String usuarioLogadoName = usuarioLogadoClass.getName();
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            if (usuarioLogadoNameTrue) {
                ModelAluno modelAlunoHttp = (ModelAluno) usuarioLogadoObject;
                Long httpCodigo = modelAlunoHttp.getAlunoId();
                ModelAluno modelAlunoBanco = repositoryAluno.findByAlunoId(httpCodigo);
                String modelAlunoName = "modelAluno";

                ModelSerie modelSerie = modelAlunoBanco.getSerie();
                Long modelAlunoSerieSerieId = modelSerie.getSerieId();
                String modelAlunoSerieSerieIdName = "modelAlunoSerieSerieId";

                String areasName = "areas";
                Iterable<ModelArea> areas = repositoryArea.findAll();

                ModelArea modelArea = modelAlunoBanco.getAreaDificuldade();
                Long modelAlunoAreaDificuldadeAreaId = modelArea.getAreaId();
                String modelAlunoAreaDificuldadeAreaIdName = "modelAlunoAreaDificuldadeAreaId";

                String viewName = "aluno/update/updateArea";

                modelAndView.addObject(modelAlunoName, modelAlunoBanco);
                modelAndView.addObject(modelAlunoSerieSerieIdName, modelAlunoSerieSerieId);
                modelAndView.addObject(areasName, areas);
                modelAndView.addObject(modelAlunoAreaDificuldadeAreaIdName, modelAlunoAreaDificuldadeAreaId);
                modelAndView.setViewName(viewName);
            }
            else {
                String viewName = "redirect:/tutor/doUpdate";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // doUpdateArea

    @PostMapping("/saveUpdateArea")
    public ModelAndView saveUpdateArea (@Valid DTOAlunoArea updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/saveUpdateSerie\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/update/updateDetailsErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String viewName = "redirect:/aluno/readOne";

            Long updatedAlunoId = updating.getAlunoId();
            ModelAluno existing = repositoryAluno.findByAlunoId(updatedAlunoId);

            Long updatedAreaDificuldadeId = updating.getAreaDificuldade();
            ModelArea updatedAreaDificuldade = repositoryArea.findByAreaId(updatedAreaDificuldadeId);

            existing.setAreaDificuldade(updatedAreaDificuldade);

            repositoryAluno.save(existing);

            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    } // saveUpdateArea

    @GetMapping("/doUpdateEmail")
    public ModelAndView doUpdateEmail (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/aluno/doUpdateEmail\n");
        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(usuarioName);
        if(usuarioLogadoObject == null) {
            String loginView = "access/login";
            modelAndView.setViewName(loginView);
        }
        else {
            Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
            String usuarioLogadoName = usuarioLogadoClass.getName();
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            if (usuarioLogadoNameTrue) {
                ModelAluno modelAlunoHttp = (ModelAluno) usuarioLogadoObject;

                Long alunoId = modelAlunoHttp.getAlunoId();
                String alunoName = modelAlunoHttp.getAlunoName();

                ModelSerie modelSerie = modelAlunoHttp.getSerie();
                Long serie = modelSerie.getSerieId();

                ModelArea modelAreaDificuldade = modelAlunoHttp.getAreaDificuldade();
                Long areaDificuldade = modelAreaDificuldade.getAreaId();

                Integer idade = modelAlunoHttp.getIdade();

                DTOAlunoArea dtoAlunoArea = new DTOAlunoArea(alunoId, alunoName, idade, serie, areaDificuldade);
                String dtoAlunoAreaName = "dtoAlunoArea";

                String viewName = "aluno/update/updateEmail";

                modelAndView.addObject(dtoAlunoAreaName, dtoAlunoArea);
                modelAndView.setViewName(viewName);
            }
            else {
                String viewName = "redirect:/tutor/doUpdateEmail";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // doUpdateEmail

    @PostMapping("/saveUpdateEmail")
    public ModelAndView saveUpdateEmail (@Valid DTOAlunoEmail updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/saveUpdateEmail\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/update/updateEmailError";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {

            String viewName = "redirect:/aluno/readOne";

            Long updatedAlunoId = updating.getAlunoId();
            ModelAluno existing = repositoryAluno.findByAlunoId(updatedAlunoId);

            String updatedEmail = updating.getAlunoEmail();

            String error = "Nothing";

            try {
                serviceAluno.validateEmailExistence(updatedEmail);
            } catch (EMailExistsException e) {
                error = e.getMessage();
            }

            if (error.equals("Nothing")) {
                existing.setAlunoEmail(updatedEmail);
                repositoryAluno.save(existing);
            }
            else {
                String errorName = "error";
                viewName = "aluno/update/updateEmailException";
                modelAndView.addObject(errorName, error);
                modelAndView.setViewName(viewName);
            }

            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    } // saveUpdateEmail

    @GetMapping("/doUpdatePassw")
    public ModelAndView doUpdatePassw (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/aluno/doUpdatePassw\n");
        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(usuarioName);
        if(usuarioLogadoObject == null) {
            String loginView = "access/login";
            modelAndView.setViewName(loginView);
        }
        else {
            Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
            String usuarioLogadoName = usuarioLogadoClass.getName();
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            if (usuarioLogadoNameTrue) {
                ModelAluno modelAlunoHttp = (ModelAluno) usuarioLogadoObject;

                Long alunoId = modelAlunoHttp.getAlunoId();
                String alunoName = modelAlunoHttp.getAlunoName();

                ModelSerie modelSerie = modelAlunoHttp.getSerie();
                Long serie = modelSerie.getSerieId();

                ModelArea modelAreaDificuldade = modelAlunoHttp.getAreaDificuldade();
                Long areaDificuldade = modelAreaDificuldade.getAreaId();

                Integer idade = modelAlunoHttp.getIdade();
                String alunoEmail = modelAlunoHttp.getAlunoEmail();

                DTOAlunoEmail dtoAlunoEmail = new DTOAlunoEmail(alunoId, alunoName, serie, areaDificuldade, idade, alunoEmail);

                String dtoAlunoEmailName = "dtoAlunoEmail";
                String viewName = "aluno/update/updatePassw";

                modelAndView.addObject(dtoAlunoEmailName, dtoAlunoEmail);
                modelAndView.setViewName(viewName);
            }
            else {
                String viewName = "redirect:/tutor/doUpdatePassw";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // doUpdatePassw

    @PostMapping("/saveUpdatePassw")
    public ModelAndView saveUpdatePassw (@Valid DTOAlunoPassword updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/saveUpdatePassw\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/update/updatePasswError";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {

            List<String> errors = new ArrayList<>();
            Long updatedAlunoId = updating.getAlunoId();
            String updatedPassw = updating.getPassw();
            ModelAluno existing = repositoryAluno.findByAlunoId(updatedAlunoId);

            try {
                serviceAluno.validatePasswordSize(updatedPassw);
            } catch (IllegalArgumentException e) {
                errors.add(e.getMessage());
            }

            try {
                if (errors.isEmpty()) {
                    existing.setPassw(updatedPassw);
                    serviceAluno.saveSignin(existing);
                    modelAndView.setViewName("redirect:/aluno/readOne");
                }
            } catch (CryptoExistsException e) {
                errors.add(e.getMessage());
            } catch (Exception e) {
                errors.add("Erro inesperado: " + e.getMessage());
            }

            if (!errors.isEmpty()) {
                modelAndView.setViewName("aluno/update/updatePasswExceptions");
                modelAndView.addObject("errors", errors);
            }
        }

        return modelAndView;
    } // saveUpdatePassw

} // ControllerAluno