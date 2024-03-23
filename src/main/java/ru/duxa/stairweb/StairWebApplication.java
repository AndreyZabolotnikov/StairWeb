package ru.duxa.stairweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.duxa.stairweb.repository.PersonRepository;

@SpringBootApplication
public class StairWebApplication {
    private static Logger log = LoggerFactory.getLogger(StairWebApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(StairWebApplication.class, args);
    }

}
