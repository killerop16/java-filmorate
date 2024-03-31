package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.validation.ReleaseDateValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

    @Test
    void testReleaseDateConstraintValid() {
        ReleaseDateValidator releaseDateValidator = new ReleaseDateValidator();

        Film film = new Film();
        film.setName("Example Film");
        film.setReleaseDate(LocalDate.of(2022, 1, 1)); // A date after the minimum allowed date

        boolean isValid = releaseDateValidator.isValid(film.getReleaseDate(), null);

        assertTrue(isValid);
    }

    @Test
    void testReleaseDateConstraintInvalid() {
        ReleaseDateValidator releaseDateValidator = new ReleaseDateValidator();

        Film film = new Film();
        film.setName("Example Film");
        film.setReleaseDate(LocalDate.of(1800, 1, 1)); // A date before the minimum allowed date

        boolean isValid = releaseDateValidator.isValid(film.getReleaseDate(), null);

        assertFalse(isValid);
    }
}