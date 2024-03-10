package ru.duxa.stairweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.duxa.stairweb.config.MyPersonDetails;
import ru.duxa.stairweb.model.Person;
import ru.duxa.stairweb.repository.PersonRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Person> person = Optional.ofNullable(personRepository.findByEmail(email));

        return person.map(MyPersonDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));

//        if (person != null) {
//            return new User(
//                    person.getEmail(),
//                    person.getPassword(),
//                    mapRolesToAuthorities(person.getRoles())
//            );
//        } else {
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
    }

//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//        return mapRoles;
//    }

}
