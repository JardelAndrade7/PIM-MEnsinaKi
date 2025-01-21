package com.msoft.mek.controller;

import com.msoft.mek.dto.DTOLogin;
import com.msoft.mek.exception.ServiceAlunoException;
import com.msoft.mek.exception.ServiceTutorException;
import com.msoft.mek.general.Util;
import com.msoft.mek.model.ModelAluno;
import com.msoft.mek.model.ModelArea;
import com.msoft.mek.model.ModelTutor;
import com.msoft.mek.repository.RepositoryAluno;
import com.msoft.mek.repository.RepositoryTutor;
import com.msoft.mek.service.ServiceAluno;
import com.msoft.mek.service.ServiceTutor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.NoSuchAlgorithmException;

@Controller
public class ControllerAccess {

    @Autowired
    private ServiceAluno serviceAluno;

    @Autowired
    private ServiceTutor serviceTutor;

    @Autowired
    private RepositoryTutor repositoryTutor;

    @Autowired
    RepositoryAluno repositoryAluno;

    // seeUserFeed verificando conta própria
    @GetMapping("/")
    public ModelAndView seeUserFeed (HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/access/seeUserFeed\n");
        String userLogadoName = "usuarioLogado";
        Object userLogado = http.getAttribute(userLogadoName);
        if(userLogado == null) {
            String loginView = "access/login";
            modelAndView.setViewName(loginView);
        }
        else {
            Class<?> userLogadoClass = userLogado.getClass();
            String userLogadoClassName = userLogadoClass.getName();
            boolean userLogadoClassNameTrue = userLogadoClassName.equals("com.msoft.mek.model.ModelTutor");
            if (userLogadoClassNameTrue) {
                ModelTutor modelTutor = (ModelTutor) userLogado;
                Long tutorId = modelTutor.getTutorId();
                ModelTutor modelTutorBanco = repositoryTutor.findByTutorId(tutorId);
                ModelArea modelArea = modelTutorBanco.getAreaAptidao();
                Long areaId = modelArea.getAreaId();
                String modelTutorFeed = "redirect:/solicitacao/readByTutorArea/" + areaId;
                modelAndView.setViewName(modelTutorFeed);
            }
            else {
                ModelAluno modelAluno = (ModelAluno) userLogado;
                Long alunoId = modelAluno.getAlunoId();
                ModelAluno modelAlunoBanco = repositoryAluno.findByAlunoId(alunoId);
                ModelArea modelArea = modelAlunoBanco.getAreaDificuldade();
                Long areaId = modelArea.getAreaId();
                String modelAlunoFeed = "redirect:/sugestao/readByAlunoArea/" + areaId;
                modelAndView.setViewName(modelAlunoFeed);
            }
        }
        return modelAndView;
    } // seeUserFeed

    @GetMapping("/accessTryLogin")
    public ModelAndView tryLogin(HttpSession http, ModelAndView modelAndView) {
        System.out.println("\n/access/tryLogin\n");
        String userLogadoName = "usuarioLogado";
        Object userLogado = http.getAttribute(userLogadoName);
        if(userLogado == null) {
            String loginView = "access/login";
            modelAndView.setViewName(loginView);
        }
        else {
            String homeView = "redirect:/";
            modelAndView.setViewName(homeView);
        }
        return modelAndView;
    } // accessTryLogin

    @PostMapping("/accessEnterLogin")
    public ModelAndView enterLogin (@Valid DTOLogin DTOLogin, BindingResult binding,
                                   HttpSession http, HttpServletResponse httpServlet, ModelAndView modelAndView)
            throws NoSuchAlgorithmException, ServiceTutorException, ServiceAlunoException {

        System.out.println("\n/access/enterLogin\n");

        boolean hasErrors = binding.hasErrors();
        // Se os campos não atenderem aos requisitos
        if(hasErrors) {
            modelAndView = Util.setViewLoginErrors(modelAndView, binding);
            return modelAndView;
        }
        // Se atenderem
        else {
            String loginDTOEMail = DTOLogin.getLoginEmail();
            String loginDTOPasswDecrypted = DTOLogin.getPassw();
            String loginDTOPasswEncrypted = Util.md5(loginDTOPasswDecrypted);
            ModelTutor modelTutorCadastrado = serviceTutor.findSignin(loginDTOEMail, loginDTOPasswEncrypted);
            ModelAluno modelAlunoCadastrado = serviceAluno.findSignin(loginDTOEMail, loginDTOPasswEncrypted);
            // Se não houver nenhum usuário cadastrado que contenha os dados inseridos
            if(modelTutorCadastrado == null && modelAlunoCadastrado == null){
                modelAndView = Util.setViewLessLogin(modelAndView);
                return modelAndView;
            }
            // Se houver
            else{
                // Se for Aluno
                if (modelTutorCadastrado == null) {

                    String usuarioLogado = "usuarioLogado";

                    String signedEMail = modelAlunoCadastrado.getAlunoEmail();

                    String loginToken = Util.gererateToken();
                    Cookie cookie = Util.createCookie(loginToken);
                    httpServlet.addCookie(cookie);

                    modelAlunoCadastrado.setToken(loginToken);
                    serviceAluno.updateToken(signedEMail, loginToken);
                    http.setAttribute(usuarioLogado, modelAlunoCadastrado);

                    String viewName = "redirect:/";
                    modelAndView.setViewName(viewName);

                    return modelAndView;
                }
                // Se for Tutor
                else {
                    String usuarioLogado = "usuarioLogado";

                    String signedEMail = modelTutorCadastrado.getTutorEmail();

                    String loginToken = Util.gererateToken();
                    Cookie cookie = Util.createCookie(loginToken);

                    modelTutorCadastrado.setToken(loginToken);
                    serviceTutor.updateToken(signedEMail, loginToken);
                    http.setAttribute(usuarioLogado, modelTutorCadastrado);

                    httpServlet.addCookie(cookie);

                    String viewName = "redirect:/";
                    modelAndView.setViewName(viewName);

                    return modelAndView;
                }
            }
        }
    } // accessEnterLogin

    @PostMapping("/accessLogout")
    public ModelAndView logout(HttpSession http, HttpServletResponse response, ModelAndView modelAndView) {

        System.out.println("\n/access/logout\n");
        http.invalidate();

        String cookieName = "loginToken";
        String tokenNull = null;
        Cookie cookie = new Cookie(cookieName, tokenNull);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        String viewName = "access/login";
        modelAndView.setViewName(viewName);
        return modelAndView;
    } // accessLogout

    @GetMapping("/accessChoiceSigninUser")
    public ModelAndView choiceSigninUser (HttpSession http, ModelAndView modelAndView) {

        System.out.println("\n/access/choiseSigninUser\n");
        String userLogadoName = "usuarioLogado";
        Object userLogado = http.getAttribute(userLogadoName);
        if(userLogado == null) {
            String choiceView = "access/choiceSigninUser";
            modelAndView.setViewName(choiceView);
        }
        else {
            String homeView = "redirect:/";
            modelAndView.setViewName(homeView);
        }
        return modelAndView;
    } // accessChoiceSigninUser

} // ControllerAccess