package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final HashMap<Integer, Film> films = new HashMap<>();

    private int nextId = 1;

    @GetMapping
    public List<Film> getFilms() {
        log.info("Get all films {}", films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        film.setId(generateId());
        log.info("Creating film {}", film);
        films.put(film.getId(),film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film with ID" + film.getId() + " not found");
        }
        log.info("Updating film {}", film);
        films.put(film.getId(),film);
        return film;
    }

    /**
     * Метод генерирует уникальный Id
     */
    public  int generateId() {
        return nextId++;
    }
}
