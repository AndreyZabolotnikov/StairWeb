package ru.duxa.stairweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.duxa.stairweb.model.Stair;

@Repository
public interface StairRepository extends JpaRepository<Stair, Long> {
    Stair findByNumberStair(Integer number);
}
