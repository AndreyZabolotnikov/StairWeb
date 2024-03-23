package ru.duxa.stairweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.duxa.stairweb.model.PasswordResetToken;
import ru.duxa.stairweb.model.Person;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByPersonId(Long id);
}
