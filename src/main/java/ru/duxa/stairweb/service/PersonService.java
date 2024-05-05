package ru.duxa.stairweb.service;

import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.model.Person;

import java.util.List;

public interface PersonService {
    void saveUser(PersonRegistrationDto personRegistrationDto);
    Person findByEmail(String email);
    List<PersonRegistrationDto> findAllUsers();

    void saveAdmin(String email, String password);

    void resetAdmin(String email,String password);

    void updatePerson(PersonRegistrationDto registrationDto);

    PersonRegistrationDto convertEntityToDto(Person person);
}
