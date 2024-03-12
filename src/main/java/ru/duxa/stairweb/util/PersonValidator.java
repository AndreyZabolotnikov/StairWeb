package ru.duxa.stairweb.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
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

        if (person.getName().isEmpty()) {
            errors.rejectValue("name", null, "Имя не должно быть пустым");
        }
        if (person.getTelephone().isEmpty()) {
            errors.rejectValue("telephone", null, "Телефон не должен быть пустым");
        }

        if (personService.findByEmail(person.getEmail()) != null)
            errors.rejectValue("email", "", "Email такой уже существует");

        if (person.getEmail().isEmpty())
            errors.rejectValue("email", null, "Email не должен быть пустым");

        if (person.getPassword().isEmpty()) {
            errors.rejectValue("password", null, "Пароль не должен быть пустым");
        }
    }
}
