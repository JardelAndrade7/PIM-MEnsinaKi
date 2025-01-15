package com.msoft.mek.controller;

import com.msoft.mek.model.ModelAluno;
import com.msoft.mek.model.ModelSolicitacao;
import com.msoft.mek.repository.RepositorySolicitacao;
import com.msoft.mek.service.ServiceSolicitacao;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/solicitacao")
public class ControllerSolicitacao {

    @Autowired
    private RepositorySolicitacao solicitacaoRepository;
    @Autowired
    private ServiceSolicitacao serviceSolicitacao;

    @GetMapping("/doCreate")
    public ModelAndView doCreate (ModelAndView modelAndView) {
        String viewName = "solicitacao/create";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @PostMapping("/saveCreate")
    public ModelAndView saveCreate (@Valid ModelSolicitacao solicitacao, BindingResult binding, HttpSession http, ModelAndView modelAndView) throws Exception {

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
            modelAndView.setViewName(viewName);
            solicitacao.setAluno(modelAluno);
            serviceSolicitacao.save(solicitacao);
        }
        return modelAndView;
    }

    @GetMapping("/readAlunoList")
    public ModelAndView readAlunoList (HttpSession http, ModelAndView modelAndView) {
        String usuarioLogadoName = "usuarioLogado";
        Object usuarioLogado = http.getAttribute(usuarioLogadoName);
        ModelAluno modelAluno = (ModelAluno) usuarioLogado;
        Iterable<ModelSolicitacao> solicitacoes = solicitacaoRepository.findByAluno(modelAluno);
        String attributeName = "solicitacoes";
        String viewName = "solicitacao/readAlunoList";
        modelAndView.setViewName(viewName);
        modelAndView.addObject(attributeName, solicitacoes);
        return modelAndView;
    }

    @GetMapping("/doUpdate/{solicitacaoId}")
    public ModelAndView doUpdate (@PathVariable("solicitacaoId") Long solicitacaoId, ModelAndView modelAndView) {
        ModelSolicitacao solicitacao = solicitacaoRepository.findBySolicitacaoId(solicitacaoId);
        String viewName = "solicitacao/update";
        String solicitacaoName = "modelSolicitacao";
        modelAndView.addObject(solicitacaoName, solicitacao);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @PostMapping("/saveUpdate")
    public ModelAndView saveUpdate (@Valid ModelSolicitacao updating, BindingResult binding, ModelAndView modelAndView) throws Exception {
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

            ModelSolicitacao existing = solicitacaoRepository.findBySolicitacaoId(updatedCodigo);

            existing.setTitle(updatedTitulo);
            existing.setModelo(updatedModelo);
            existing.setLocal(updatedAmbiente);

            serviceSolicitacao.update(existing);

            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    }

    @GetMapping("/delete/{solicitacaoId}")
    public ModelAndView delete (@PathVariable("solicitacaoId") Long solicitacaoId, ModelAndView modelAndView) {
        String viewName = "redirect:/solicitacao/readAlunoList";
        solicitacaoRepository.deleteById(solicitacaoId);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

} // ControllerSolicitacao