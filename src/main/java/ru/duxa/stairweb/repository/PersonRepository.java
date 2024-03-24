package ru.duxa.stairweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.duxa.stairweb.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);
}
