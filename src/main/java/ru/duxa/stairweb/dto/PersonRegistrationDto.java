package ru.duxa.stairweb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonRegistrationDto {
    private Long id;
    @NotEmpty(message = "Имя не должно быть пустым")
    private String name;
    private String middleName;
    private String lastName;
    private String organization;
    @NotEmpty(message = "Номер телефона не должен быть пустым")
    private String telephone;
    @NotEmpty(message = "Email не должен быть пустым")
    @Email
    private String email;
    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;
    private LocalDateTime dateCreate;
}
