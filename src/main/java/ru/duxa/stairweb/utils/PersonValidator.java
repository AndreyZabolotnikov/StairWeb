package ru.duxa.stairweb.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.duxa.stairweb.model.Person;
import ru.duxa.stairweb.repository.PersonRepository;

@Component
public class PersonValidator implements Validator {
    private final PersonRepository personRepository;

    @Autowired
    public PersonValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (person.getEmail() != null && person.getEmail().length() > 0) {
            if (!person.getEmail().contains("@")) {
                errors.rejectValue("email", "", "Некорректный email");
            }
        }
    }
}
