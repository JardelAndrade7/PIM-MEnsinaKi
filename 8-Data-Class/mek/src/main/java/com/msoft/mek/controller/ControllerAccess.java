package com.msoft.mek.controller;

import com.msoft.mek.exception.ServiceAlunoException;
import com.msoft.mek.exception.ServiceTutorException;
import com.msoft.mek.general.DTOLogin;
import com.msoft.mek.general.Util;
import com.msoft.mek.model.ModelAluno;
import com.msoft.mek.model.ModelTutor;
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

    @GetMapping("/")
    public ModelAndView seeUserFeed (HttpSession http, HttpServletResponse httpServlet, ModelAndView modelAndView) {
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
                String modelTutorFeed = "tutor/feed";
                modelAndView.setViewName(modelTutorFeed);
            }
            else {
                String modelAlunoFeed = "aluno/feed";
                modelAndView.setViewName(modelAlunoFeed);
            }
        }
        return modelAndView;
    }

    @GetMapping("/accessTryLogin")
    public ModelAndView tryLogin(HttpSession http, ModelAndView modelAndView) {
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
    }

    @PostMapping("/accessEnterLogin")
    public ModelAndView enterLogin (@Valid DTOLogin DTOLogin, BindingResult binding,
                                   HttpSession http, HttpServletResponse httpServlet, ModelAndView modelAndView)
            throws NoSuchAlgorithmException, ServiceTutorException, ServiceAlunoException {
        String loginDTOEMail = DTOLogin.getLoginEmail();
        String loginDTOPasswDecrypted = DTOLogin.getPassw();
        String loginDTOPasswEncrypted = Util.md5(loginDTOPasswDecrypted);
        boolean hasErrors = binding.hasErrors();
        if(hasErrors) {
            String errors = "errors";
            String viewName = "access/loginErrors";
            modelAndView.addObject(errors, binding.getAllErrors());
            modelAndView.setViewName(viewName);
            return modelAndView;
        }
        ModelTutor modelTutorCadastrado = serviceTutor.findSignin(loginDTOEMail, loginDTOPasswEncrypted);
        ModelAluno modelAlunoCadastrado = serviceAluno.findSignin(loginDTOEMail, loginDTOPasswEncrypted);
        if(modelTutorCadastrado == null && modelAlunoCadastrado == null){
            String msgName = "msg";
            String msgNotFound = "Login não encontrado.";
            modelAndView.addObject(msgName, msgNotFound);
            String viewName = "access/lessLogin";
            modelAndView.setViewName(viewName);
            return modelAndView;
        } else{
            if (modelTutorCadastrado == null) {

                String usuarioLogado = "usuarioLogado";

                String loginToken = ServiceAluno.gererateToken();
                String cookieName = "loginToken";
                Cookie cookie = new Cookie(cookieName, loginToken);

                String signedEMail = modelAlunoCadastrado.getAlunoEmail();
                modelAlunoCadastrado.setToken(loginToken);
                serviceAluno.updateToken(signedEMail, loginToken);
                http.setAttribute(usuarioLogado, modelAlunoCadastrado);

                cookie.setMaxAge(7 * 24 * 60 * 60);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                httpServlet.addCookie(cookie);

                String viewName = "aluno/feed";
                modelAndView.setViewName(viewName);

                return modelAndView;

            }
            else {

                String usuarioLogado = "usuarioLogado";

                String loginToken = ServiceTutor.gererateToken();
                String cookieName = "loginToken";
                Cookie cookie = new Cookie(cookieName, loginToken);

                String signedEMail = modelTutorCadastrado.getTutorEmail();
                modelTutorCadastrado.setToken(loginToken);
                serviceTutor.updateToken(signedEMail, loginToken);
                http.setAttribute(usuarioLogado, modelTutorCadastrado);

                cookie.setMaxAge(7 * 24 * 60 * 60);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                httpServlet.addCookie(cookie);

                String viewName = "tutor/feed";
                modelAndView.setViewName(viewName);

                return modelAndView;

            }
        }
    }

    @PostMapping("/accessLogout")
    public ModelAndView logout(HttpSession http, HttpServletResponse response, ModelAndView modelAndView) {
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
    }

    @GetMapping("/accessChoiceSigninUser")
    public ModelAndView choiceSigninUser (HttpSession http, ModelAndView modelAndView) {
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
    }

} // ControllerAccess