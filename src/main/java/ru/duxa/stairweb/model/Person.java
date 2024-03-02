package ru.duxa.stairweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "organization")
    private String organization;

    @Column(name = "telephone")
    @NotBlank(message = "Номер телефона не может быть пустым")
    private Long telephone;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный email")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @PrePersist
    private void onCreate() {
        dateCreate = LocalDateTime.now();
    }

}
