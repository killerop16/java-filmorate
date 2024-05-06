package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    private Set<Integer> friendsId = new HashSet<>();
    private int id;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Login cannot be blank")
    private String login;

    private String name;


    @Past(message = "Date of birth cannot be in the future")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;


    public String getDisplayName() {
        return name == null || name.isEmpty() ? login : name;
    }

    public User(int id, String email, String login, String name, LocalDate of) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = of;
    }
}
