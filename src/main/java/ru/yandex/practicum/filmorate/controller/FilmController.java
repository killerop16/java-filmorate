package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final FilmServiceImpl filmService;

    @GetMapping
    public List<Film> getFilms() {
        log.info("Get all films {}", filmService.getAll().size());
        return filmService.getAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Creating film {}", film);
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Updating film {}", film);
        filmService.updateFilm(film);
        return film;
    }

    @GetMapping("/users{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Get film with ID {}", id);
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("User likes the movie with ID {}", id);
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film delLike(@PathVariable int id, @PathVariable int userId) {
        log.info("User removes a like from a movie with an ID {}", id);
        return filmService.delLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilm(@RequestParam Integer count) throws Exception {
        log.info("Get a list of the top movies by number of likes {}", count);
        return filmService.getTopFilm(count);
    }

    @GetMapping("/{id}")
    public Film getGenreByFilmId(@PathVariable int id) {
        log.info("Get Film by ID {}", id);
        return  getFilmById(id);
    }
}
