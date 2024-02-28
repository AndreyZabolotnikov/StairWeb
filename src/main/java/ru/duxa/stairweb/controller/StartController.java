package ru.duxa.stairweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

    @GetMapping
    public String startWeb(){
        return "index";
    }

    @GetMapping("/authorization")
    public String authorizationWeb() {
        return "authorization";
    }

    @GetMapping("/reg")
    public String regWeb() {
        return "reg";
    }
}
