package ru.duxa.stairweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.service.PersonService;


@Controller
@RequestMapping("/reg")
public class RegController {

    private final PersonService personService;


    @Autowired
    public RegController(PersonService personService) {
        this.personService = personService;
    }

    @ModelAttribute("person")
    public PersonRegistrationDto personRegistrationDto() {
        return new PersonRegistrationDto();
    }

    @GetMapping
    public String regWeb() {
        return "reg";
    }

    @PostMapping
    public String regPerson(@ModelAttribute("person") PersonRegistrationDto registrationDto ) {
       try{
           personService.save(registrationDto);
       }
       catch (Exception e){
           System.out.println(e);
           return "redirect:reg?email_invalid";
       }
        return "redirect:authorization";
    }
}
