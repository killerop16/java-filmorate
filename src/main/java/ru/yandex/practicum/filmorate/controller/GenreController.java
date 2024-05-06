package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public List<Genre> getAll() {
        log.info("Get all genres {}", genreService.getAll().size());
        return genreService.getAll();
    }

    @GetMapping("/genres/{id}")
    public Genre getById(@PathVariable int id) {
        log.info("Get genre by id = {}", id);
        return genreService.getById(id);
    }
}
