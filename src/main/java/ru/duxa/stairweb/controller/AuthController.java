package ru.duxa.stairweb.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.model.Person;
import ru.duxa.stairweb.service.PersonService;

import java.util.List;


@Controller
public class AuthController {

    private final PersonService personService;

    @Autowired
    public AuthController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/authorization")
    public String authorizationWeb() {
        return "authorization";
    }

    @GetMapping("/reg")
    public String regWeb(Model model) {
        PersonRegistrationDto personRegistrationDto = new PersonRegistrationDto();
        model.addAttribute("person", personRegistrationDto);
        return "reg";
    }

    @PostMapping("/reg/save")
    public String regPerson(@Valid @ModelAttribute("person") PersonRegistrationDto registrationDto,
                            BindingResult result, Model model) {
        Person existing = personService.findByEmail(registrationDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }
        if (result.hasErrors()) {
            model.addAttribute("person", registrationDto);
            return "reg";
        }
        personService.saveUser(registrationDto);
        return "redirect:authorization";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<PersonRegistrationDto> users = personService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

}
