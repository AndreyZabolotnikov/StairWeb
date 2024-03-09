package ru.duxa.stairweb.service;

import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.model.Person;

import java.util.List;

public interface PersonService {
    void saveUser(PersonRegistrationDto personRegistrationDto);
    Person findByEmail(String email);
    List<PersonRegistrationDto> findAllUsers();
}
