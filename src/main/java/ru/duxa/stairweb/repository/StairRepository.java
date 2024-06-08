package ru.duxa.stairweb.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import ru.duxa.stairweb.model.Stair;

@Repository
public interface StairRepository extends JpaRepository<Stair, Long> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Stair findByNumberStair(Integer number);
}
