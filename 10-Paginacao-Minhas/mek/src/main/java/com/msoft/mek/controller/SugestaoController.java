package com.msoft.mek.controller;

import com.msoft.mek.general.DTOSugestao;
import com.msoft.mek.model.ModelArea;
import com.msoft.mek.model.ModelSugestao;
import com.msoft.mek.model.ModelTutor;
import com.msoft.mek.repository.RepositoryArea;
import com.msoft.mek.repository.RepositorySugestao;
import com.msoft.mek.service.ServiceSugestao;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sugestao")
public class SugestaoController {

    @Autowired
    private RepositorySugestao repositorySugestao;
    @Autowired
    private ServiceSugestao serviceSugestao;
    @Autowired
    private RepositoryArea repositoryArea;

    @GetMapping("/doCreate")
    public ModelAndView doCreate (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/sugestao/doCreate\n");
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
            String comMmSoft = "com.msoft.mek.model.ModelTutor";
            boolean usuarioLogadoNameTrue = usuarioLogadoClassName.equals(comMmSoft);
            // Se for Tutor
            if (usuarioLogadoNameTrue) {
                Iterable<ModelArea> areas = repositoryArea.findAll();
                String areasName = "areas";
                modelAndView.addObject(areasName, areas);
                String viewName = "sugestao/create";
                modelAndView.setViewName(viewName);
            }
            // Se for Aluno
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    }

    @PostMapping("/saveCreate")
    public ModelAndView saveCreate (@Valid DTOSugestao dtoSugestao, BindingResult binding, HttpSession http, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/sugestaoR/saveCreate\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "sugestao/createErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
        }
        else {
            String viewName = "redirect:/";
            String usuarioLogadoName = "usuarioLogado";
            Object usuarioLogado = http.getAttribute(usuarioLogadoName);
            ModelTutor modelTutor = (ModelTutor) usuarioLogado;
            ModelSugestao modelSugestao = new ModelSugestao();
            ModelArea modelArea;

            String title = dtoSugestao.getTitle();
            String modelo = dtoSugestao.getModelo();
            String local = dtoSugestao.getLocal();
            Double price = dtoSugestao.getPrice();
            Long areaId = dtoSugestao.getAreaId();

            modelArea = repositoryArea.findByAreaId(areaId);
            
            modelSugestao.setTitle(title);
            modelSugestao.setModelo(modelo);
            modelSugestao.setLocal(local);
            modelSugestao.setPrice(price);
            modelSugestao.setTutor(modelTutor);
            modelSugestao.setArea(modelArea);
            serviceSugestao.save(modelSugestao);

            modelAndView.setViewName(viewName);
        }
        return modelAndView;
    }

    @GetMapping("/readTutorList")
    public ModelAndView readTutorList (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/sugestao/readTutorList\n");
        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(usuarioName);
        // Se o usuário não estiver logado
        if(usuarioLogadoObject == null) {
            String loginView = "access/login";
            modelAndView.setViewName(loginView);
        }
        // Se o usuário estiver logado
        else {
            Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
            String usuarioLogadoClassName = usuarioLogadoClass.getName();
            String comMmSoft = "com.msoft.mek.model.ModelTutor";
            boolean usuarioLogadoNameTrue = usuarioLogadoClassName.equals(comMmSoft);
            // Se for Tutor
            if (usuarioLogadoNameTrue) {
                String usuarioLogadoName = "usuarioLogado";
                Object usuarioLogado = http.getAttribute(usuarioLogadoName);
                ModelTutor modelTutor = (ModelTutor) usuarioLogado;
                Iterable<ModelSugestao> sugestoes = repositorySugestao.findByCodigoTutor(modelTutor);
                String attributeName = "sugestoes";
                String viewName = "sugestao/readTutorList";
                modelAndView.setViewName(viewName);
                modelAndView.addObject(attributeName, sugestoes);
            }
            // Se for Aluno
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // readTutorList

    @GetMapping("/doUpdate/{sugestaoId}")
    public ModelAndView doUpdate (@PathVariable("sugestaoId") Long sugestaoId, HttpSession http, ModelAndView modelAndView) {

        System.out.println("\n/sugestao/doUpdate\n");

        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(usuarioName);
        // Se o usuário não estiver logado
        if(usuarioLogadoObject == null) {
            String loginView = "access/login";
            modelAndView.setViewName(loginView);
        }
        // Se o usuário estiver logado
        else {
            Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
            String usuarioLogadoName = usuarioLogadoClass.getName();
            String comMmSoft = "com.msoft.mek.model.ModelTutor";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            // Se for Tutor
            if (usuarioLogadoNameTrue) {
                Iterable<ModelArea> areas = repositoryArea.findAll();
                String areasName = "areas";
                ModelSugestao sugestao = repositorySugestao.findBySugestaoId(sugestaoId);
                String viewName = "sugestao/update";
                String sugestaoName = "modelSugestao";
                ModelArea modelArea = sugestao.getArea();
                Long modelSugestaoAreaAreaId = modelArea.getAreaId();
                String modelSugestaoAreaAreaIdName = "modelSugestaoAreaAreaId";
                modelAndView.addObject(sugestaoName, sugestao);
                modelAndView.addObject(areasName, areas);
                modelAndView.addObject(modelSugestaoAreaAreaIdName, modelSugestaoAreaAreaId);
                modelAndView.setViewName(viewName);
            }
            // Se for Aluno
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // doUpdate

    @PostMapping("/saveUpdate")
    public ModelAndView saveUpdate (@Valid DTOSugestao updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/sugestao/saveUpdate\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "sugestao/atualizacaoErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String viewName = "redirect:/sugestao/readTutorList";

            Long updatedCodigo = updating.getSugestaoId();
            String updatedTitulo = updating.getTitle();
            String updatedModelo = updating.getModelo();
            String updatedLocal = updating.getLocal();
            Double updatedPrice = updating.getPrice();
            Long updatedAreaId = updating.getAreaId();
            ModelArea modelArea = repositoryArea.findByAreaId(updatedAreaId);

            ModelSugestao existing = repositorySugestao.findBySugestaoId(updatedCodigo);

            existing.setTitle(updatedTitulo);
            existing.setModelo(updatedModelo);
            existing.setLocal(updatedLocal);
            existing.setPrice(updatedPrice);
            existing.setArea(modelArea);

            serviceSugestao.update(existing);

            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    }

    @GetMapping("/delete/{sugestaoId}")
    public ModelAndView delete (@PathVariable("sugestaoId") Long sugestaoId, HttpSession http, ModelAndView modelAndView) {

        System.out.println("\n/sugestao/delete\n");

        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = http.getAttribute(usuarioName);
        // Se o usuário não estiver logado
        if(usuarioLogadoObject == null) {
            String loginView = "access/login";
            modelAndView.setViewName(loginView);
        }
        // Se o usuário estiver logado
        else {
            Class<?> usuarioLogadoClass = usuarioLogadoObject.getClass();
            String usuarioLogadoName = usuarioLogadoClass.getName();
            String comMmSoft = "com.msoft.mek.model.ModelTutor";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            // Se for Tutor
            if (usuarioLogadoNameTrue) {
                String viewName = "redirect:/sugestao/readTutorList";
                repositorySugestao.deleteById(sugestaoId);
                modelAndView.setViewName(viewName);
            }
            // Se for Aluno
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    }

} // SugestaoController