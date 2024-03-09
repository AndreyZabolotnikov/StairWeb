package ru.duxa.stairweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

    @GetMapping("index")
    public String startWeb(){
        return "index";
    }

    @GetMapping("/authorization")
    public String authorizationWeb() {
        return "authorization";
    }


}
