package com.msoft.mek.general;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;

public class Util {
    public static String md5 (String passw) throws NoSuchAlgorithmException {
        MessageDigest message = MessageDigest.getInstance("md5");
        BigInteger hash = new BigInteger(1, message.digest(passw.getBytes()));
        String hashString = hash.toString(16);
        return hashString;
    }

    public static String gererateToken() {
        return UUID.randomUUID().toString();
    }

    public static Cookie createCookie (String loginToken) {
        String cookieName = "loginToken";
        Cookie cookie = new Cookie(cookieName, loginToken);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    public static ModelAndView setViewLessLogin(ModelAndView modelAndView) {
        String msgName = "msg";
        String msgNotFound = "Login não encontrado.";
        modelAndView.addObject(msgName, msgNotFound);
        String viewName = "access/lessLogin";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    public static ModelAndView setViewLoginErrors(ModelAndView modelAndView, BindingResult bindingResult){
        String errors = "errors";
        String viewName = "access/loginErrors";
        modelAndView.addObject(errors, bindingResult.getAllErrors());
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
