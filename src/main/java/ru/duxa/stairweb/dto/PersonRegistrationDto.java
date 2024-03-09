package ru.duxa.stairweb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PersonRegistrationDto {
    private Long id;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    private String middleName;
    private String lastName;
    private String organization;
    @NotEmpty(message = "Telephone should not be empty")
    private Long telephone;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    private LocalDateTime dateCreate;
}
