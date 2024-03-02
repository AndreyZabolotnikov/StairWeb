package ru.duxa.stairweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.duxa.stairweb.repository.PersonRepository;

@Controller
public class StartController {

    @Autowired
    private  PersonRepository personRepository;

    @GetMapping
    public String startWeb(){
        return "index";
    }

    @GetMapping("/authorization")
    public String authorizationWeb() {
        return "authorization";
    }


}
