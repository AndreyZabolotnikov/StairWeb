package ru.duxa.stairweb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

    @GetMapping("/")
    public String startWeb(Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute("isAuth", true);
            model.addAttribute("user", authentication.getName());
        }
        else {
            model.addAttribute("isAuth", false);
        }
        return "index";
    }

}
