package ru.duxa.stairweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.duxa.stairweb.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
