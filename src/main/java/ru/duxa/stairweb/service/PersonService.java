package ru.duxa.stairweb.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.model.Person;

import java.util.List;

public interface PersonService extends UserDetailsService {
    Person save(PersonRegistrationDto personRegistrationDto);
    List<Person> getAll();
}
