package ru.duxa.stairweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.model.Role;
import ru.duxa.stairweb.repository.RoleRepository;
import ru.duxa.stairweb.service.PersonService;

import java.util.List;

@Controller
public class AdminController {

    private final RoleRepository roleRepository;
    private final PersonService personService;

    @Autowired
    public AdminController(RoleRepository roleRepository, PersonService personService) {
        this.roleRepository = roleRepository;
        this.personService = personService;
    }

    @GetMapping("/admin")
    public String listRegisteredUsers1(Model model) {
        List<PersonRegistrationDto> users = personService.findAllUsers();
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "admin";
    }

}
