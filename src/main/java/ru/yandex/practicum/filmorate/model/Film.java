package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
public class Film {
    private Set<Integer> likes = new HashSet<>();
    private int id;

    @NotBlank(message = "Name must not be empty")
    private String name;

    @Size(max = 200, message = "Description length should not exceed 200 characters")
    private String description;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @ReleaseDateConstraint
    private LocalDate releaseDate;

    @Positive(message = "Duration should be a positive number")
    private int duration;
}
