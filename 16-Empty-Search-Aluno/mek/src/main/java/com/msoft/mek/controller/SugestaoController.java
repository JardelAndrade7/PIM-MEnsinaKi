package com.msoft.mek.controller;

import com.msoft.mek.dto.DTOSugestao;
import com.msoft.mek.model.ModelArea;
import com.msoft.mek.model.ModelSugestao;
import com.msoft.mek.model.ModelTutor;
import com.msoft.mek.repository.RepositoryArea;
import com.msoft.mek.repository.RepositorySugestao;
import com.msoft.mek.service.ServiceSugestao;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            String viewName = "redirect:/sugestao/readTutorList/" + 0;
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

    @GetMapping("/readTutorList/{page}")
    public ModelAndView readTutorList (
    @PathVariable("page") int page,
    @RequestParam(defaultValue = "2") int size,
    HttpSession http,
    ModelAndView modelAndView) {
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

                String pageName = "page";

                Pageable pageable = PageRequest.of(page, size);
                Page<ModelSugestao> sugestoesPage = repositorySugestao.findByTutor(modelTutor, pageable);

                List<ModelSugestao> sugestoesList = sugestoesPage.getContent();

                Integer totalPages = sugestoesPage.getTotalPages();
                String totalPagesName = "totalPages";

                String sugestoesName = "sugestoes";
                String viewName = "sugestao/readTutorList";
                modelAndView.setViewName(viewName);
                modelAndView.addObject(sugestoesName, sugestoesList);

                modelAndView.addObject(pageName, page);
                modelAndView.addObject(totalPagesName, totalPages);
            }
            // Se for Aluno
            else {
                String viewName = "redirect:/solicitacao/readAlunoList";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // readTutorList

    @GetMapping("/readByAlunoArea/{areaId}")
    public ModelAndView readByAlunoArea (
    @PathVariable("areaId") long areaId,
    /* @PathVariable("page") long page, */
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "2") int size,
    HttpSession http,
    ModelAndView modelAndView) {

        System.out.println("\n/sugestao/readByAlunoArea\n");
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
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoClassName.equals(comMmSoft);
            // Se for Aluno
            if (usuarioLogadoNameTrue) {

                String areaIdName = "areaId";
                ModelArea modelArea = repositoryArea.findByAreaId(areaId);
                String pageName = "page";

                Pageable pageable = PageRequest.of(page, size);
                Page<ModelSugestao> sugestoesPage = repositorySugestao.findByArea(modelArea, pageable);

                List<ModelSugestao> sugestoesList = sugestoesPage.getContent();

                Integer totalPages = sugestoesPage.getTotalPages();
                String totalPagesName = "totalPages";

                String sugestoesName = "sugestoes";
                String viewName = "aluno/feed";
                modelAndView.setViewName(viewName);
                modelAndView.addObject(sugestoesName, sugestoesList);

                modelAndView.addObject(pageName, page);
                modelAndView.addObject(totalPagesName, totalPages);
                modelAndView.addObject(areaIdName, areaId);
            }
            // Se for Tutor
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // readByAlunoArea

    @GetMapping("/readByOtherArea/{areaId}")
    public ModelAndView readByOtherArea (
    @PathVariable("areaId") long areaId,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "2") int size,
    HttpSession http,
    ModelAndView modelAndView) {

        System.out.println("\n/sugestao/readByOtherArea\n");
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
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoClassName.equals(comMmSoft);
            // Se for Aluno
            if (usuarioLogadoNameTrue) {

                String areaIdName = "areaId";
                ModelArea modelArea = repositoryArea.findByAreaId(areaId);
                String pageName = "page";
                String modelAreaName = "modelArea";

                Pageable pageable = PageRequest.of(page, size);
                Page<ModelSugestao> sugestoesPage = repositorySugestao.findByArea(modelArea, pageable);

                List<ModelSugestao> sugestoesList = sugestoesPage.getContent();

                Integer totalPages = sugestoesPage.getTotalPages();
                String totalPagesName = "totalPages";

                String sugestoesName = "sugestoes";
                String viewName = "sugestao/readByOtherArea";
                modelAndView.setViewName(viewName);
                modelAndView.addObject(sugestoesName, sugestoesList);

                modelAndView.addObject(pageName, page);
                modelAndView.addObject(totalPagesName, totalPages);
                modelAndView.addObject(areaIdName, areaId);
                modelAndView.addObject(modelAreaName, modelArea);
            }
            // Se for Tutor
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // readByOtherArea

    @GetMapping("/readOne/{sugestaoId}")
    public ModelAndView readOne(
        HttpSession httpSession,
        ModelAndView modelAndView,
        @PathVariable("sugestaoId") long sugestaoId
    ) {
        System.out.println("\n/solicitacao/readOne\n");
        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = httpSession.getAttribute(usuarioName);
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
                ModelSugestao sugestao = repositorySugestao.findBySugestaoId(sugestaoId);
                String sugestaoName = "sugestao";
                String viewName = "sugestao/readOne";
                ModelArea areaSugestao = sugestao.getArea();
                String modelAreaName = "areaSugestao";
                ModelTutor modelTutor = sugestao.getTutor();
                String modelTutorName = "modelTutor";
                ModelArea areaAptidao = modelTutor.getAreaAptidao();
                String areaAptidaoName = "areaAptidao";
                modelAndView.addObject(sugestaoName, sugestao);
                modelAndView.addObject(modelAreaName, areaSugestao);
                modelAndView.addObject(modelTutorName, modelTutor);
                modelAndView.addObject(areaAptidaoName, areaAptidao);
                modelAndView.setViewName(viewName);
            }
            // Se for Tutor
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // readOne

    @PostMapping("/searchByAreaAndTitle")
    public ModelAndView searchByAreaAndTitle (
        Long areaId,
        String titleSearch,
        ModelAndView modelAndView,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size,
        HttpSession httpSession
        ){

        boolean titleSearchTrimEmpty = titleSearch.trim().isEmpty();

        if(titleSearch == null || titleSearchTrimEmpty == true){
            return readByAlunoArea(areaId, page, size, httpSession, modelAndView);
        }
        else {

            Pageable pageable = PageRequest.of(page, size);

            Page<ModelSugestao> sugestoesPage = repositorySugestao.findByAreaIdAndTitle(
                areaId, titleSearch, pageable
            );
    
            List<ModelSugestao> sugestoesList = sugestoesPage.getContent();
            String sugestoesName = "sugestoes";
    
            Integer totalPages = sugestoesPage.getTotalPages();
            String totalPagesName = "totalPages";
    
            String pageName = "page";
            String titleSearchName = "titleSearch";
    
            String viewName = "sugestao/searchByAreaAndTitle";

            String areaIdName = "areaId";
    
            modelAndView.addObject(pageName, page);
            modelAndView.addObject(totalPagesName, totalPages);
            modelAndView.addObject(sugestoesName, sugestoesList);
            modelAndView.addObject(titleSearchName, titleSearch);
            modelAndView.addObject(areaIdName, areaId);
            modelAndView.setViewName(viewName);

        }

        return modelAndView;
    } // searchByAreaAndTitle

    @PostMapping("/searchByTutorAndTitle")
    public ModelAndView searchByTutorAndTitle (
        HttpSession httpSession,
        String titleSearch,
        ModelAndView modelAndView,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size
        ){

        System.out.println("\n/sugestao/searchByTutorAndTitle\n");

        boolean titleSearchTrimEmpty = titleSearch.trim().isEmpty();

        if(titleSearch == null || titleSearchTrimEmpty == true){
            String viewName = "redirect:/solicitacao/readAlunoList";
            modelAndView.setViewName(viewName);
        }
        else {

            String usuarioName = "usuarioLogado";
            Object usuarioLogadoObject = httpSession.getAttribute(usuarioName);
            ModelTutor modelTutor = (ModelTutor) usuarioLogadoObject;

            Pageable pageable = PageRequest.of(page, size);

            Page<ModelSugestao> sugestoesPage = repositorySugestao.findByTutorAndTitle(
                modelTutor, titleSearch, pageable
            );
    
            List<ModelSugestao> sugestoesList = sugestoesPage.getContent();
            String sugestoesName = "sugestoes";
    
            Integer totalPages = sugestoesPage.getTotalPages();
            String totalPagesName = "totalPages";
    
            String pageName = "page";
            String titleSearchName = "titleSearch";
    
            String viewName = "sugestao/searchByTutorAndTitle";
    
            modelAndView.addObject(pageName, page);
            modelAndView.addObject(totalPagesName, totalPages);
            modelAndView.addObject(sugestoesName, sugestoesList);
            modelAndView.addObject(titleSearchName, titleSearch);
            modelAndView.setViewName(viewName);

        }

        return modelAndView;
    } // searchByTutorAndTitle

    @GetMapping("/doUpdate/{sugestaoId}")
    public ModelAndView doUpdate (@PathVariable("sugestaoId") Long sugestaoId, int page, HttpSession http, ModelAndView modelAndView) {

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
                String pageName = "page";
                modelAndView.addObject(pageName, page);
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
    public ModelAndView saveUpdate (@Valid DTOSugestao updating, @RequestParam int page, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/sugestao/saveUpdate\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "sugestao/atualizacaoErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String viewName = "redirect:/sugestao/readTutorList/" + page;

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
    public ModelAndView delete (@PathVariable("sugestaoId") Long sugestaoId, Integer page, HttpSession http, ModelAndView modelAndView) {

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
                String viewName = "redirect:/sugestao/readTutorList/" + page;
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
    } // delete

} // SugestaoController