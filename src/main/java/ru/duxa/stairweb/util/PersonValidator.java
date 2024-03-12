package ru.duxa.stairweb.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.model.Person;
import ru.duxa.stairweb.service.PersonServiceImpl;

@Component
public class PersonValidator implements Validator {

    private final PersonServiceImpl personService;

    @Autowired
    public PersonValidator(PersonServiceImpl personService) {
        this.personService = personService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonRegistrationDto person = (PersonRegistrationDto) target;
        try {
            personService.findByEmail(person.getEmail());
        } catch (UsernameNotFoundException e) {
            return;
        }
        if (personService.findByEmail(person.getEmail()) == null) {
            errors.rejectValue("email", null, "Email не должен быть пустым");
        } else
            errors.rejectValue("email", "", "Email такой уже существует");
    }
}
