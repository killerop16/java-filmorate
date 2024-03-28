package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmsService {
    Film addFilm(Film film);

    List<Film> getAll();

    Film updateFilm(Film film);
}
