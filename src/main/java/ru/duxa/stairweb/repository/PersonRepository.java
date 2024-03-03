package ru.duxa.stairweb.repository;

import org.springframework.data.repository.CrudRepository;
import ru.duxa.stairweb.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
