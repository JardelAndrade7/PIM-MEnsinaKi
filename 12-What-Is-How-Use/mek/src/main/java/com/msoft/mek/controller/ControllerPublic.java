package com.msoft.mek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class ControllerPublic {

    @GetMapping("/howToUse")
    public String howToUse () {
        String viewName = "public/howToUse";
        return viewName;
    }

    @GetMapping("/whatIs")
    public String whatIs () {
        String viewName = "public/whatIs";
        return viewName;
    }

}