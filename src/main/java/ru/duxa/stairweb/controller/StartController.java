package ru.duxa.stairweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.model.Role;
import ru.duxa.stairweb.repository.RoleRepository;
import ru.duxa.stairweb.service.PersonService;

import java.util.List;
import java.util.Optional;

@Controller
public class StartController {

    private final PersonService personService;
    private final RoleRepository roleRepository;

    @Autowired
    public StartController(PersonService personService, RoleRepository roleRepository) {
        this.personService = personService;
        this.roleRepository = roleRepository;
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

    @GetMapping("/admin")
    public String listRegisteredUsers1(Model model){
        List<PersonRegistrationDto> users = personService.findAllUsers();
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "admin";
    }

}
