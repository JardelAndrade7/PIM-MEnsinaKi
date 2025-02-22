package com.msoft.mek.controller;

import com.msoft.mek.dto.DTOSolicitacao;
import com.msoft.mek.model.ModelAluno;
import com.msoft.mek.model.ModelArea;
import com.msoft.mek.model.ModelSerie;
import com.msoft.mek.model.ModelSolicitacao;
import com.msoft.mek.repository.RepositoryArea;
import com.msoft.mek.repository.RepositorySolicitacao;
import com.msoft.mek.service.ServiceSolicitacao;
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
@RequestMapping("/solicitacao")
public class ControllerSolicitacao {

    @Autowired
    private RepositorySolicitacao repositorySolicitacao;

    @Autowired
    private ServiceSolicitacao serviceSolicitacao;

    @Autowired
    private RepositoryArea repositoryArea;

    @GetMapping("/doCreate")
    public ModelAndView doCreate (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/solicitacao/doCreate\n");
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
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoClassName.equals(comMmSoft);
            // Se for Aluno
            if (usuarioLogadoNameTrue) {
                Iterable<ModelArea> areas = repositoryArea.findAll();
                String areasName = "areas";
                modelAndView.addObject(areasName, areas);
                String viewName = "solicitacao/aluno/create";
                modelAndView.setViewName(viewName);
            }
            // Se for Tutor
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    }

    @PostMapping("/saveCreate")
    public ModelAndView saveCreate (@Valid DTOSolicitacao dtoSolicitacao, BindingResult binding, HttpSession http, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/solicitacao/saveCreate\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "solicitacao/aluno/createErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
        }
        else {
            String viewName = "redirect:/solicitacao/readAlunoList/" + 0;
            String usuarioLogadoName = "usuarioLogado";
            Object usuarioLogado = http.getAttribute(usuarioLogadoName);
            ModelAluno modelAluno = (ModelAluno) usuarioLogado;
            ModelSolicitacao modelSolicitacao = new ModelSolicitacao();

            String title = dtoSolicitacao.getTitle();
            String modelo = dtoSolicitacao.getModelo();
            String local = dtoSolicitacao.getLocal();
            Long areaId = dtoSolicitacao.getAreaId();

            ModelArea modelArea = repositoryArea.findByAreaId(areaId);
            
            modelSolicitacao.setTitle(title);
            modelSolicitacao.setModelo(modelo);
            modelSolicitacao.setLocal(local);
            modelSolicitacao.setAluno(modelAluno);
            modelSolicitacao.setArea(modelArea);
            serviceSolicitacao.save(modelSolicitacao);

            modelAndView.setViewName(viewName);
        }
        return modelAndView;
    }

    @GetMapping("/readOne/{solicitacaoId}")
    public ModelAndView readOne(
        HttpSession httpSession,
        ModelAndView modelAndView,
        @PathVariable("solicitacaoId") long solicitacaoId
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
            String comMmSoft = "com.msoft.mek.model.ModelTutor";
            boolean usuarioLogadoNameTrue = usuarioLogadoClassName.equals(comMmSoft);
            // Se for Tutor
            if (usuarioLogadoNameTrue) {
                ModelSolicitacao solicitacao = repositorySolicitacao.findBySolicitacaoId(solicitacaoId);
                String solicitacaoName = "solicitacao";
                String viewName = "solicitacao/tutor/readOne";
                ModelArea areaSolicitacao = solicitacao.getArea();
                String modelAreaName = "areaSolicitacao";
                ModelAluno modelAluno = solicitacao.getAluno();
                String modelAlunoName = "modelAluno";
                ModelArea areaDificuldade = modelAluno.getAreaDificuldade();
                String areaDificuldadeName = "areaDificuldade";
                ModelSerie modelSerie = modelAluno.getSerie();
                String modelSerieName = "modelSerie";
                modelAndView.addObject(solicitacaoName, solicitacao);
                modelAndView.addObject(modelAreaName, areaSolicitacao);
                modelAndView.addObject(modelAlunoName, modelAluno);
                modelAndView.addObject(areaDificuldadeName, areaDificuldade);
                modelAndView.addObject(modelSerieName, modelSerie);
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

    @GetMapping("/readAlunoList/{page}")
    public ModelAndView readAlunoList (
        @PathVariable("page")
        int page,
        @RequestParam(defaultValue = "2")
        int size,
        HttpSession http,
        ModelAndView modelAndView)
        {

        System.out.println("\n/solicitacao/readAlunoList\n");
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
                Object usuarioLogado = http.getAttribute(usuarioName);
                ModelAluno modelAluno = (ModelAluno) usuarioLogado;

                String pageName = "page";

                Pageable pageable = PageRequest.of(page, size);
                Page<ModelSolicitacao> solicitacoesPage = repositorySolicitacao.findByAluno(modelAluno, pageable);

                List<ModelSolicitacao> solicitacoesList = solicitacoesPage.getContent();

                Integer totalPages = solicitacoesPage.getTotalPages();
                String totalPagesName = "totalPages";

                String solicitacoesName = "solicitacoes";
                String viewName = "solicitacao/aluno/readAlunoList";
                modelAndView.setViewName(viewName);
                modelAndView.addObject(solicitacoesName, solicitacoesList);

                modelAndView.addObject(pageName, page);
                modelAndView.addObject(totalPagesName, totalPages);
            }
            // Se for Tutor
            else {
                String viewName = "redirect:/sugestao/readTutorList";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // readAlunoList

    @GetMapping("/readByTutorArea/{areaId}")
    public ModelAndView readByTutorArea (
    @PathVariable("areaId") long areaId,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "2") int size,
    HttpSession http,
    ModelAndView modelAndView) {

        System.out.println("\n/solicitacao/readByTutorArea\n");
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
                ModelArea modelArea = repositoryArea.findByAreaId(areaId);
                String pageName = "page";
                String areaIdName = "areaId";

                Pageable pageable = PageRequest.of(page, size);
                Page<ModelSolicitacao> solicitacoesPage = repositorySolicitacao.findByArea(modelArea, pageable);

                List<ModelSolicitacao> solicitacoesList = solicitacoesPage.getContent();

                Integer totalPages = solicitacoesPage.getTotalPages();
                String totalPagesName = "totalPages";

                String solicitacoesName = "solicitacoes";
                String viewName = "tutor/feed";
                modelAndView.setViewName(viewName);
                modelAndView.addObject(solicitacoesName, solicitacoesList);

                modelAndView.addObject(pageName, page);
                modelAndView.addObject(totalPagesName, totalPages);
                modelAndView.addObject(areaIdName, areaId);
            }
            // Se for Aluno
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // readByTutorArea

    // readTutorList readByAlunoArea readByOtherArea

    @GetMapping("/readByOtherArea/{areaId}")
    public ModelAndView readByOtherArea (
    @PathVariable("areaId") Long areaId,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "2") int size,
    HttpSession http,
    ModelAndView modelAndView) {

        System.out.println("\n/solicitacao/readByOtherArea\n");
        System.out.println();
        System.out.println(areaId);
        System.out.println();
        
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
                System.out.println();
                System.out.println("if (usuarioLogadoNameTrue)");
                System.out.println(areaId);
                System.out.println();
                ModelArea modelArea = repositoryArea.findByAreaId(areaId);
                String pageName = "page";
                String areaIdName = "areaId";
                String areaName = modelArea.getAreaName();
                String areaNameName = "areaName";

                Pageable pageable = PageRequest.of(page, size);
                Page<ModelSolicitacao> solicitacoesPage = repositorySolicitacao.findByArea(modelArea, pageable);

                List<ModelSolicitacao> solicitacoesList = solicitacoesPage.getContent();

                Integer totalPages = solicitacoesPage.getTotalPages();
                String totalPagesName = "totalPages";

                String solicitacoesName = "solicitacoes";
                String viewName = "solicitacao/tutor/readByOtherArea";

                modelAndView.setViewName(viewName);
                modelAndView.addObject(solicitacoesName, solicitacoesList);

                modelAndView.addObject(pageName, page);
                modelAndView.addObject(totalPagesName, totalPages);

                System.out.println();
                System.out.println("Antes addObject");
                System.out.println(areaId);
                System.out.println();

                modelAndView.addObject(areaIdName, areaId);
                modelAndView.addObject(areaNameName, areaName);
            }
            // Se for Aluno
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // readByOtherArea

    @PostMapping("/searchByAreaAndTitle")
    public ModelAndView searchByAreaAndTitle (
        Long areaId,
        String titleSearch,
        Integer pageSearch,
        ModelAndView modelAndView,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size,
        HttpSession httpSession
        ){

        System.out.println("\n/solicitacao/searchByAreaAndTitle\n");

        boolean titleSearchTrimEmpty = titleSearch.trim().isEmpty();

        if(titleSearch == null || titleSearchTrimEmpty == true){
            return readByTutorArea(areaId, page, size, httpSession, modelAndView);
        }
        else {

            Pageable pageable = PageRequest.of(pageSearch, size);

            Page<ModelSolicitacao> solicitacoesPage = repositorySolicitacao.findByAreaIdAndTitle(
                areaId, titleSearch, pageable
            );
    
            List<ModelSolicitacao> solicitacoesList = solicitacoesPage.getContent();
            String solicitacoesName = "solicitacoes";
    
            Integer totalPages = solicitacoesPage.getTotalPages();
            String totalPagesName = "totalPages";
    
            String pageName = "page";
            String pageSearchName = "pageSearch";
            String titleSearchName = "titleSearch";
            String areaIdName = "areaId";
    
            String viewName = "solicitacao/tutor/searchByAreaAndTitle";
    
            modelAndView.addObject(pageName, page);
            modelAndView.addObject(pageSearchName, pageSearch);
            modelAndView.addObject(totalPagesName, totalPages);
            modelAndView.addObject(solicitacoesName, solicitacoesList);
            modelAndView.addObject(titleSearchName, titleSearch);
            modelAndView.addObject(areaIdName, areaId);
            modelAndView.setViewName(viewName);

        }

        return modelAndView;
    } // searchByAreaAndTitle

    @PostMapping("/searchByOtherAreaAndTitle")
    public ModelAndView searchByOtherAreaAndTitle (
        Long areaId,
        String titleSearch,
        Integer pageSearch,
        ModelAndView modelAndView,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size,
        HttpSession httpSession
        ){

        System.out.println("\n/solicitacao/searchByOtherAreaAndTitle\n");
        System.out.println();
        System.out.println(areaId);
        System.out.println();

        boolean titleSearchTrimEmpty = titleSearch.trim().isEmpty();

        if(titleSearch == null || titleSearchTrimEmpty == true){
            return readByOtherArea(areaId, page, size, httpSession, modelAndView);
        }
        else {

            Pageable pageable = PageRequest.of(pageSearch, size);

            Page<ModelSolicitacao> solicitacoesPage = repositorySolicitacao.findByAreaIdAndTitle(
                areaId, titleSearch, pageable
            );
    
            List<ModelSolicitacao> solicitacoesList = solicitacoesPage.getContent();
            String solicitacoesName = "solicitacoes";
    
            Integer totalPages = solicitacoesPage.getTotalPages();
            String totalPagesName = "totalPages";
    
            String pageName = "page";
            String pageSearchName = "pageSearch";
            String titleSearchName = "titleSearch";
            String areaIdName = "areaId";
    
            String viewName = "solicitacao/tutor/searchByAreaAndTitle";
    
            modelAndView.addObject(pageName, page);
            modelAndView.addObject(pageSearchName, pageSearch);
            modelAndView.addObject(totalPagesName, totalPages);
            modelAndView.addObject(solicitacoesName, solicitacoesList);
            modelAndView.addObject(titleSearchName, titleSearch);
            modelAndView.addObject(areaIdName, areaId);
            modelAndView.setViewName(viewName);

        }

        return modelAndView;
    } // searchByOtherAreaAndTitle

    @PostMapping("/searchByAlunoAndTitle")
    public ModelAndView searchByAlunoAndTitle (
        HttpSession httpSession,
        String titleSearch,
        Integer pageSearch,
        ModelAndView modelAndView,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size
        ){

        System.out.println("\n/solicitacao/searchByAlunoAndTitle\n");

        boolean titleSearchTrimEmpty = titleSearch.trim().isEmpty();

        if(titleSearch == null || titleSearchTrimEmpty == true){
            String viewName = "redirect:/solicitacao/readAlunoList/" + page;
            modelAndView.setViewName(viewName);
        }
        else {

            String usuarioName = "usuarioLogado";
            Object usuarioLogadoObject = httpSession.getAttribute(usuarioName);
            ModelAluno modelAluno = (ModelAluno) usuarioLogadoObject;

            Pageable pageable = PageRequest.of(pageSearch, size);

            Page<ModelSolicitacao> solicitacoesPage = repositorySolicitacao.findByAlunoAndTitle(
                modelAluno, titleSearch, pageable
            );
    
            List<ModelSolicitacao> solicitacoesList = solicitacoesPage.getContent();
            String solicitacoesName = "solicitacoes";
    
            Integer totalPages = solicitacoesPage.getTotalPages();
            String totalPagesName = "totalPages";
    
            String pageName = "page";
            String pageSearchName = "pageSearch";
            String titleSearchName = "titleSearch";
    
            String viewName = "solicitacao/aluno/searchByAlunoAndTitle";
    
            modelAndView.addObject(pageName, page);
            modelAndView.addObject(pageSearchName, pageSearch);
            modelAndView.addObject(totalPagesName, totalPages);
            modelAndView.addObject(solicitacoesName, solicitacoesList);
            modelAndView.addObject(titleSearchName, titleSearch);
            modelAndView.setViewName(viewName);

        }

        return modelAndView;
    } // searchByAlunoAndTitle

    @GetMapping("/doUpdate/{solicitacaoId}")
    public ModelAndView doUpdate (@PathVariable("solicitacaoId") Long solicitacaoId, int page, HttpSession http, ModelAndView modelAndView) {

        System.out.println("\n/solicitacao/doUpdate\n");

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
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            // Se for Aluno
            if (usuarioLogadoNameTrue) {
                Iterable<ModelArea> areas = repositoryArea.findAll();
                String areasName = "areas";
                ModelSolicitacao solicitacao = repositorySolicitacao.findBySolicitacaoId(solicitacaoId);
                String viewName = "solicitacao/aluno/update";
                String solicitacaoName = "modelSolicitacao";
                ModelArea modelArea = solicitacao.getArea();
                Long modelSolicitacaoAreaAreaId = modelArea.getAreaId();
                String modelSolicitacaoAreaAreaIdName = "modelSolicitacaoAreaAreaId";
                String pageName = "page";
                modelAndView.addObject(solicitacaoName, solicitacao);
                modelAndView.addObject(areasName, areas);
                modelAndView.addObject(modelSolicitacaoAreaAreaIdName, modelSolicitacaoAreaAreaId);
                modelAndView.addObject(pageName, page);
                modelAndView.setViewName(viewName);
            }
            // Se for Tutor
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // doUpdate

    @PostMapping("/saveUpdate")
    public ModelAndView saveUpdate (@Valid DTOSolicitacao updating, BindingResult binding, @RequestParam int page, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/solicitacao/saveUpdate\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "solicitacao/aluno/updateErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String viewName = "redirect:/solicitacao/readAlunoList/" + page;

            Long updatedCodigo = updating.getSolicitacaoId();
            String updatedTitulo = updating.getTitle();
            String updatedModelo = updating.getModelo();
            String updatedAmbiente = updating.getLocal();
            Long updatedAreaId = updating.getAreaId();
            ModelArea modelArea = repositoryArea.findByAreaId(updatedAreaId);

            ModelSolicitacao existing = repositorySolicitacao.findBySolicitacaoId(updatedCodigo);

            existing.setTitle(updatedTitulo);
            existing.setModelo(updatedModelo);
            existing.setLocal(updatedAmbiente);
            existing.setArea(modelArea);

            serviceSolicitacao.update(existing);

            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    } // saveUpdate

    public int getElementsPage (int page, int size, HttpSession http) {
        System.out.println("\n/solicitacao/getElementsPage\n");

        String usuarioLogadoName = "usuarioLogado";
        Object usuarioLogado = http.getAttribute(usuarioLogadoName);
        ModelAluno modelAluno = (ModelAluno) usuarioLogado;

        Pageable pageable = PageRequest.of(page, size);
        Page<ModelSolicitacao> solicitacoesPage = repositorySolicitacao.findByAluno(modelAluno, pageable);

        int elementsPage = solicitacoesPage.getNumberOfElements();
        return elementsPage;
    } // getElementsPage

    public int getElementsSearchAlunoTitle (
        HttpSession httpSession,
        String titleSearch,
        Integer pageSearch,
        int page,
        int size
    )
    {
        System.out.println("\n/solicitacao/getElementsSearchAlunoTitle\n");

        String usuarioName = "usuarioLogado";
        Object usuarioLogadoObject = httpSession.getAttribute(usuarioName);
        ModelAluno modelAluno = (ModelAluno) usuarioLogadoObject;

        Pageable pageable = PageRequest.of(pageSearch, size);

        Page<ModelSolicitacao> solicitacoesPage = repositorySolicitacao.findByAlunoAndTitle(
            modelAluno, titleSearch, pageable
        );

        int elementsPage = solicitacoesPage.getNumberOfElements();
        return elementsPage;
    } // getElementsSearchAlunoTitle

    @GetMapping("/delete/{solicitacaoId}")
    public ModelAndView delete (@PathVariable("solicitacaoId") Long solicitacaoId, Integer page, HttpSession http, ModelAndView modelAndView) {

        System.out.println("\n/solicitacao/delete\n");

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
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            // Se for Aluno
            if (usuarioLogadoNameTrue) {
                repositorySolicitacao.deleteById(solicitacaoId);

                int size = 2;
                int elementsPage = getElementsPage(page, size, http);

                if(page > 0 && elementsPage == 0){
                    page = page - 1;
                }

                String viewName = "redirect:/solicitacao/readAlunoList/" + page;
                modelAndView.setViewName(viewName);
            }
            // Se for Tutor
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // delete

    @GetMapping("/deleteSearchAlunoTitle/{solicitacaoId}")
    public ModelAndView deleteSearchAlunoTitle (@PathVariable("solicitacaoId") Long solicitacaoId, String titleSearch, Integer pageSearch, Integer page, HttpSession http, ModelAndView modelAndView) {

        System.out.println("\n/solicitacao/deleteSearchAlunoTitle\n");

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
            String comMmSoft = "com.msoft.mek.model.ModelAluno";
            boolean usuarioLogadoNameTrue = usuarioLogadoName.equals(comMmSoft);
            // Se for Aluno
            if (usuarioLogadoNameTrue) {
                repositorySolicitacao.deleteById(solicitacaoId);

                int size = 2;
                int elementsPage = getElementsSearchAlunoTitle(http, titleSearch, pageSearch, page, size);

                if(pageSearch > 0 && elementsPage == 0){
                    pageSearch = pageSearch - 1;
                }

                return searchByAlunoAndTitle(http, titleSearch, pageSearch, modelAndView, page, size);
            }
            // Se for Tutor
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // deleteSearchAlunoTitle

} // ControllerSolicitacao