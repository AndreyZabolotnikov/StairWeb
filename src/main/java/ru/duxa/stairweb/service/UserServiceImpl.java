package ru.duxa.stairweb.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.model.Person;
import ru.duxa.stairweb.model.Role;
import ru.duxa.stairweb.model.Roles;
import ru.duxa.stairweb.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private PersonRepository personRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Person save(PersonRegistrationDto registrationDto) {
        var person = new Person(registrationDto.getName(),
                registrationDto.getMiddleName(),
                registrationDto.getLastName(),
                registrationDto.getOrganization(),
                registrationDto.getTelephone(),
                registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()),
                Arrays.asList(new Role(Roles.ROLE_USER.toString()))
                );
        return personRepository.save(person);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var person = personRepository.findByEmail(username);
        if(person != null){
            throw new UsernameNotFoundException("Пользователь с таким email не найден");
        }
        return new org.springframework.security.core.userdetails.User(person.getEmail(), person.getPassword(), mapRolesToAuthorities(person.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }
}
