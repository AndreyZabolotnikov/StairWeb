package ru.duxa.stairweb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordForgotDto {
    @Email
    @NotEmpty
    private String email;
}
