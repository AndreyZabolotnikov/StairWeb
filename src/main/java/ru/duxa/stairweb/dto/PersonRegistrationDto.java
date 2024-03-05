package ru.duxa.stairweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PersonRegistrationDto {
    private String name;
    private String middleName;
    private String lastName;
    private String organization;
    private Long telephone;
    private String email;
    private String password;
    private LocalDateTime dateCreate;
}
