package ru.duxa.stairweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.duxa.stairweb.repository.PersonRepository;

@SpringBootApplication
public class StairWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(StairWebApplication.class, args);
    }

}
