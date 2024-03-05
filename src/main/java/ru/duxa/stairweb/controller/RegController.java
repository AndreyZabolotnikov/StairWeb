package ru.duxa.stairweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.duxa.stairweb.model.Person;
import ru.duxa.stairweb.repository.PersonRepository;
import ru.duxa.stairweb.utils.PersonValidator;

@Controller
public class RegController {

    private final PersonRepository personRepository;
    private final PersonValidator personValidator;

    @Autowired
    public RegController(PersonRepository personRepository, PersonValidator personValidator) {
        this.personRepository = personRepository;
        this.personValidator = personValidator;
    }

    @GetMapping("/reg")
    public String regWeb(@ModelAttribute("person") Person person) {
        return "reg";
    }

    @PostMapping("/reg")
    public String regPerson(@ModelAttribute("person") Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "reg";
        }

        personRepository.save(person);
        return "redirect:authorization";
    }
}
