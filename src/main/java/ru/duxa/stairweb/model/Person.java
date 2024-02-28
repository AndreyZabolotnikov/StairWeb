package ru.duxa.stairweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

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
    private int telephone;

    @Column(name = "email")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @Column(name = "date")
    private LocalDateTime date;

    @PrePersist
    private void onCreate() {
        date = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
