package ru.duxa.stairweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.service.PersonService;

import java.util.List;

@Controller
public class StartController {

    private final PersonService personService;

    @Autowired
    public StartController(PersonService personService) {
        this.personService = personService;
    }

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

    @GetMapping("/users")
//    @PreAuthorize("hasRole('USER')")
    public String listRegisteredUsers(Model model){
        List<PersonRegistrationDto> users = personService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users1")
    public String listRegisteredUsers1(Model model){
        List<PersonRegistrationDto> users = personService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

}
