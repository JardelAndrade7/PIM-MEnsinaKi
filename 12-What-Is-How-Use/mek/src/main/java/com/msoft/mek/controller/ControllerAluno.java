package com.msoft.mek.controller;

import com.msoft.mek.dto.DTOAluno;
import com.msoft.mek.dto.DTOUpdateAluno;
import com.msoft.mek.dto.DTOUpdateEmailAluno;
import com.msoft.mek.dto.DTOUpdatePasswAluno;
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

    @GetMapping("/doSignin")
    public ModelAndView doSignin (HttpSession http, ModelAndView modelAndView) {

        System.out.println("\n/aluno/doSignin\n");
        String userLogadoName = "usuarioLogado";
        Object userLogado = http.getAttribute(userLogadoName);
        if(userLogado == null) {
            String areasName = "areas";
            Iterable<ModelArea> areas = repositoryArea.findAll();
            String seriesName = "series";
            Iterable<ModelSerie> series = repositorySerie.findAll();
            String viewName = "aluno/signin";
            modelAndView.addObject(areasName, areas);
            modelAndView.addObject(seriesName, series);
            modelAndView.setViewName(viewName);
        }
        else {
            String viewName = "redirect:/";
            modelAndView.setViewName(viewName);
        }
        return modelAndView;
    }

    @PostMapping("/saveSignin")
    public ModelAndView saveSignin(@Valid DTOAluno dtoAluno, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/seveSignin\n");
        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/signinErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            List<String> errors = new ArrayList<>();
            String alunoName = dtoAluno.getAlunoName();

            Long serieId = dtoAluno.getSerie();
            ModelSerie serie = repositorySerie.findBySerieId(serieId);

            Long areaDificuldadeId = dtoAluno.getAreaDificuldade();
            ModelArea areaDificuldade = repositoryArea.findByAreaId(areaDificuldadeId);

            Integer idade = dtoAluno.getIdade();
            String alunoEmail = dtoAluno.getAlunoEmail();
            String passw = dtoAluno.getPassw();

            try {
                serviceAluno.validatePasswordSize(passw);
            } catch (IllegalArgumentException e) {
                errors.add(e.getMessage());
            }

            try {
                serviceAluno.validateEmailExistence(alunoEmail);
            } catch (EMailExistsException e) {
                errors.add(e.getMessage());
            }

            try {
                if (errors.isEmpty()) {
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
                modelAndView.setViewName("aluno/signinExceptions");
                modelAndView.addObject("errors", errors);
            }

            return modelAndView;
        }
    } // saveSignin

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

    @GetMapping("/doUpdate")
    public ModelAndView doUpdate (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/aluno/doUpdate\n");
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
                String viewName = "aluno/update";

                String areasName = "areas";
                Iterable<ModelArea> areas = repositoryArea.findAll();
                ModelArea modelArea = modelAlunoBanco.getAreaDificuldade();
                String modelAlunoAreaDificuldadeAreaIdName = "modelAlunoAreaDificuldadeAreaId";
                Long modelAlunoAreaDificuldadeAreaId = modelArea.getAreaId();

                String seriesName = "series";
                Iterable<ModelSerie> series = repositorySerie.findAll();
                ModelSerie modelSerie = modelAlunoBanco.getSerie();
                String modelAlunoSerieSerieIdName = "modelAlunoSerieSerieId";
                Long modelAlunoSerieSerieId = modelSerie.getSerieId();

                modelAndView.addObject(seriesName, series);
                modelAndView.addObject(modelAlunoSerieSerieIdName, modelAlunoSerieSerieId);
                modelAndView.addObject(areasName, areas);
                modelAndView.addObject(modelAlunoAreaDificuldadeAreaIdName, modelAlunoAreaDificuldadeAreaId);
                modelAndView.addObject(modelAlunoName, modelAlunoBanco);
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
    public ModelAndView saveUpdate (@Valid DTOUpdateAluno updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/saveUpdate\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/atualizacaoSigninErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String viewName = "redirect:/";

            Long updatedAlunoId = updating.getAlunoId();
            String updatedNome = updating.getAlunoName();
            Long updatedSerieId = updating.getSerie();
            ModelSerie updatedSerie = repositorySerie.findBySerieId(updatedSerieId);
            Long updatedAreaDificuldadeId = updating.getAreaDificuldade();
            ModelArea updatedAreaDificuldade = repositoryArea.findByAreaId(updatedAreaDificuldadeId);
            Integer updatedIdade = updating.getIdade();

            ModelAluno existing = repositoryAluno.findByAlunoId(updatedAlunoId);

            existing.setAlunoName(updatedNome);
            existing.setSerie(updatedSerie);
            existing.setAreaDificuldade(updatedAreaDificuldade);
            existing.setIdade(updatedIdade);

            repositoryAluno.save(existing);

            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    } // saveUpdate

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
                String modelAlunoHttpName = "modelAluno";

                String viewName = "aluno/updateEmail";

                modelAndView.addObject(modelAlunoHttpName, modelAlunoHttp);
                modelAndView.setViewName(viewName);
            }
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // doUpdateEmail

    @PostMapping("/saveUpdateEmail")
    public ModelAndView saveUpdateEmail (@Valid DTOUpdateEmailAluno updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/saveUpdateEmail\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/atualizacaoSigninErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {

            String viewName = "redirect:/aluno/readOne";

            Long updatedAlunoId = updating.getAlunoId();
            String updatedEmail = updating.getAlunoEmail();

            ModelAluno existing = repositoryAluno.findByAlunoId(updatedAlunoId);

            String error = "Nothing";

            try {
                serviceAluno.validateEmailExistence(updatedEmail);
            } catch (EMailExistsException e) {
                error = e.getMessage();
            }

            if (error == "Nothing") {
                existing.setAlunoEmail(updatedEmail);
                repositoryAluno.save(existing);
            }
            else {
                String errorName = "error";
                viewName = "aluno/updateEmailError";
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
                String modelAlunoHttpName = "modelAluno";
                String viewName = "aluno/updatePassw";

                modelAndView.addObject(modelAlunoHttpName, modelAlunoHttp);
                modelAndView.setViewName(viewName);
            }
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // doUpdatePassw

    @PostMapping("/saveUpdatePassw")
    public ModelAndView saveUpdatePassw (@Valid DTOUpdatePasswAluno updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/aluno/saveUpdatePassw\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "aluno/atualizacaoSigninErrors";
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
                modelAndView.setViewName("aluno/updatePasswErrors");
                modelAndView.addObject("errors", errors);
            }
        }

        return modelAndView;
    } // saveUpdatePassw

} // ControllerAluno