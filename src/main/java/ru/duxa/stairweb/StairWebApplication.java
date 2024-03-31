package ru.duxa.stairweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.duxa.stairweb.controller.AuthController;
import ru.duxa.stairweb.dto.PersonRegistrationDto;
import ru.duxa.stairweb.model.Person;
import ru.duxa.stairweb.model.Role;
import ru.duxa.stairweb.repository.PersonRepository;
import ru.duxa.stairweb.repository.RoleRepository;
import ru.duxa.stairweb.service.PersonServiceImpl;

import java.util.Arrays;

@SpringBootApplication
public class StairWebApplication {
    private static Logger log = LoggerFactory.getLogger(StairWebApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StairWebApplication.class, args);
    }

}

