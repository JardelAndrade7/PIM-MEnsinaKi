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
                String viewName = "solicitacao/create";
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
            String viewName = "solicitacao/createErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
        }
        else {
            String viewName = "redirect:/";
            String usuarioLogadoName = "usuarioLogado";
            Object usuarioLogado = http.getAttribute(usuarioLogadoName);
            ModelAluno modelAluno = (ModelAluno) usuarioLogado;
            ModelSolicitacao modelSolicitacao = new ModelSolicitacao();
            ModelArea modelArea;

            String title = dtoSolicitacao.getTitle();
            String modelo = dtoSolicitacao.getModelo();
            String local = dtoSolicitacao.getLocal();
            Long areaId = dtoSolicitacao.getAreaId();

            modelArea = repositoryArea.findByAreaId(areaId);
            
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
                String viewName = "solicitacao/readOne";
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

    @GetMapping("/readAlunoList")
    public ModelAndView readAlunoList (
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "2") int size,
    HttpSession http,
    ModelAndView modelAndView) {

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
                String usuarioLogadoName = "usuarioLogado";
                Object usuarioLogado = http.getAttribute(usuarioLogadoName);
                ModelAluno modelAluno = (ModelAluno) usuarioLogado;

                String pageName = "page";

                Pageable pageable = PageRequest.of(page, size);
                Page<ModelSolicitacao> solicitacoesPage = repositorySolicitacao.findByAluno(modelAluno, pageable);

                List<ModelSolicitacao> solicitacoesList = solicitacoesPage.getContent();

                Integer totalPages = solicitacoesPage.getTotalPages();
                String totalPagesName = "totalPages";

                String solicitacoesName = "solicitacoes";
                String viewName = "solicitacao/readAlunoList";
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

    @GetMapping("/readByArea/{areaId}")
    public ModelAndView readByArea (
    @PathVariable("areaId") long areaId,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "2") int size,
    HttpSession http,
    ModelAndView modelAndView) {

        System.out.println("\n/solicitacao/readByArea\n");
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
            }
            // Se for Aluno
            else {
                String viewName = "redirect:/";
                modelAndView.setViewName(viewName);
            }
        }
        return modelAndView;
    } // readByArea

    @GetMapping("/doUpdate/{solicitacaoId}")
    public ModelAndView doUpdate (@PathVariable("solicitacaoId") Long solicitacaoId, HttpSession http, ModelAndView modelAndView) {

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
                String viewName = "solicitacao/update";
                String solicitacaoName = "modelSolicitacao";
                ModelArea modelArea = solicitacao.getArea();
                Long modelSolicitacaoAreaAreaId = modelArea.getAreaId();
                String modelSolicitacaoAreaAreaIdName = "modelSolicitacaoAreaAreaId";
                modelAndView.addObject(solicitacaoName, solicitacao);
                modelAndView.addObject(areasName, areas);
                modelAndView.addObject(modelSolicitacaoAreaAreaIdName, modelSolicitacaoAreaAreaId);
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
    public ModelAndView saveUpdate (@Valid DTOSolicitacao updating, BindingResult binding, ModelAndView modelAndView) throws Exception {

        System.out.println("\n/solicitacao/saveUpdate\n");

        if (binding.hasErrors()) {
            String errors = "errors";
            String viewName = "solicitacao/atualizacaoErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        else {
            String viewName = "redirect:/solicitacao/readAlunoList";

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
    }

    @GetMapping("/delete/{solicitacaoId}")
    public ModelAndView delete (@PathVariable("solicitacaoId") Long solicitacaoId, HttpSession http, ModelAndView modelAndView) {

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
                String viewName = "redirect:/solicitacao/readAlunoList";
                repositorySolicitacao.deleteById(solicitacaoId);
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

} // ControllerSolicitacao