package ru.duxa.stairweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.duxa.stairweb.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);
}
