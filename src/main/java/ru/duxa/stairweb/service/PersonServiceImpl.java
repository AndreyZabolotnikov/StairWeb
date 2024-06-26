package ru.duxa.stairweb.service;

import jakarta.transaction.Transactional;
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
        person.setEnabled(true);
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

    public PersonRegistrationDto convertEntityToDto(Person person) {
        PersonRegistrationDto personRegistrationDto = new PersonRegistrationDto();
        personRegistrationDto.setEmail(person.getEmail());
        personRegistrationDto.setName(person.getName());
        personRegistrationDto.setMiddleName(person.getMiddleName());
        personRegistrationDto.setLastName(person.getLastName());
        personRegistrationDto.setOrganization(person.getOrganization());
        personRegistrationDto.setTelephone(person.getTelephone());

        return personRegistrationDto;
    }

    private Role checkRoleExists() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }


    public void saveAdmin(String email,String password) {
        Person person = new Person();
        person.setName("admin");
        person.setMiddleName("admin");
        person.setLastName("admin");
        person.setOrganization("admin");
        person.setTelephone("8900000000");
        person.setEmail(email);
        person.setPassword(passwordEncoder.encode(password));
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        person.setRoles(Arrays.asList(role));
        person.setEnabled(true);
        personRepository.save(person);
    }

    public void resetAdmin(String email,String password) {
       Person person = personRepository.findByEmail(email);
       person.setPassword(passwordEncoder.encode(password));
       personRepository.save(person);
    }

    @Override
    @Transactional
    public void updatePerson(PersonRegistrationDto registrationDto) {

        Person person = personRepository.findByEmail(registrationDto.getEmail());

        if(registrationDto.getPassword() != null && passwordEncoder.matches(registrationDto.getPassword(), person.getPassword())) {

            person.setName(registrationDto.getName());
            person.setMiddleName(registrationDto.getMiddleName());
            person.setLastName(registrationDto.getLastName());
            person.setOrganization(registrationDto.getOrganization());
            person.setTelephone(registrationDto.getTelephone());
        }
    }

    public boolean isPasswordMatch(PersonRegistrationDto registrationDto) {
        Person person = personRepository.findByEmail(registrationDto.getEmail());
        return passwordEncoder.matches(registrationDto.getPassword(), person.getPassword());
    }

}
