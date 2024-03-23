package ru.duxa.stairweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.model.Person;
import ru.duxa.stairweb.model.Role;
import ru.duxa.stairweb.repository.PersonRepository;
import ru.duxa.stairweb.repository.RoleRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository,
                             RoleRepository roleRepository,
                             PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(PersonRegistrationDto personRegistrationDto) {
        Person person = new Person();
        person.setName(personRegistrationDto.getName());
        person.setMiddleName(personRegistrationDto.getMiddleName());
        person.setLastName(personRegistrationDto.getLastName());
        person.setOrganization(personRegistrationDto.getOrganization());
        person.setTelephone(personRegistrationDto.getTelephone());
        person.setEmail(personRegistrationDto.getEmail().toLowerCase());
        person.setPassword(passwordEncoder.encode(personRegistrationDto.getPassword()));
        Role role = roleRepository.findByName("USER");
        if (role == null) {
            role = checkRoleExists();
        }
        person.setRoles(Arrays.asList(role));
        person.setDateCreate(personRegistrationDto.getDateCreate());
        personRepository.save(person);
    }

    @Override
    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Override
    public List<PersonRegistrationDto> findAllUsers() {
        List<Person> persons = personRepository.findAll();
        return persons.stream().map((person -> convertEntityToDto(person))).
                collect(Collectors.toList());
    }

    private PersonRegistrationDto convertEntityToDto(Person person) {
        PersonRegistrationDto personRegistrationDto = new PersonRegistrationDto();
        personRegistrationDto.setName(person.getName());
        personRegistrationDto.setMiddleName(person.getMiddleName());
        personRegistrationDto.setEmail(person.getEmail());
        return personRegistrationDto;
    }

    private Role checkRoleExists() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }
}
