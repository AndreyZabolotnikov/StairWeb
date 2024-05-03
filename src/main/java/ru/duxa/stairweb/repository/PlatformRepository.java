package ru.duxa.stairweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.duxa.stairweb.model.Platform;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {
    Platform findByName(String name);
}
