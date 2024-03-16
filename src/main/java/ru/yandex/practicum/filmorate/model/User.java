package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {

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
}
