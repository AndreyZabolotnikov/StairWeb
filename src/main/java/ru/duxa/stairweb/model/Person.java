package ru.duxa.stairweb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_role",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Collection<Role> roles;

    public Person(String name, String middleName, String lastName, String organization, Long telephone, String email, String password, Collection<Role> roles) {
        this.name = name;
        this.middleName = middleName;
        this.lastName = lastName;
        this.organization = organization;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

}
